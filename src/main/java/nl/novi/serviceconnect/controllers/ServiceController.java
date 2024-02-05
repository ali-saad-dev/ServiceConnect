package nl.novi.serviceconnect.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.novi.serviceconnect.dtos.ServiceInputDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.dtos.ServiceRequestInputDto;
import nl.novi.serviceconnect.dtos.ServiceRequestOutputDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.helpper.TokenHelper;
import nl.novi.serviceconnect.services.IServiceService;
import nl.novi.serviceconnect.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static nl.novi.serviceconnect.helpper.StringHelpers.getObjectResponseEntity;

@RestController
@RequestMapping("/services")
public class ServiceController {
    private final IServiceService service;
    private final JwtUtil jwtUtil;
    public ServiceController(IServiceService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping
    public ResponseEntity<Object> createService(@Valid @RequestBody ServiceInputDto serviceInputDto, HttpServletRequest request, BindingResult br) {

        if (br.hasFieldErrors()) {
            return getObjectResponseEntity(br);
        }
        else {
            String user = TokenHelper.getUserNameFromToken(request , jwtUtil);

            ServiceOutputDto outputDto = service.createService(serviceInputDto, user);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + outputDto.id).toUriString());

            return ResponseEntity.created(uri).body(outputDto);
        }
    }

    @GetMapping
    public ResponseEntity<List<ServiceOutputDto>> getAllService() {
        return ResponseEntity.ok(service.getAllService());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOutputDto> getServiceById(@PathVariable Long id) {
        ServiceOutputDto serviceId = service.getServiceById(id);
        return ResponseEntity.ok(serviceId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceOutputDto> updateService(@PathVariable Long id, @RequestBody ServiceInputDto serviceInputDto) {
        try {
            ServiceOutputDto updatedService = service.updateService(id, serviceInputDto);
            return ResponseEntity.ok(updatedService);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceInputDto> deleteService(@PathVariable Long id) {
        try {
            service.deleteService(id);
            return ResponseEntity.ok().build();
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity<Object> acceptServiceRequest(@PathVariable Long id, @Valid @RequestBody ServiceRequestInputDto inputDto, HttpServletRequest request, BindingResult br) {
        if (br.hasFieldErrors()) {
            return getObjectResponseEntity(br);
        }
        else {
            String user = TokenHelper.getUserNameFromToken(request , jwtUtil);

            ServiceRequestOutputDto serviceRequest = service.acceptServiceRequest(inputDto, id, user);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .toUriString());

            return ResponseEntity.created(uri).body(serviceRequest);
        }
    }
}
