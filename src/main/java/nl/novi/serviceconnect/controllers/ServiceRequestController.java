package nl.novi.serviceconnect.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.novi.serviceconnect.dtos.ServiceRequestInputDto;
import nl.novi.serviceconnect.dtos.ServiceRequestOutputDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.helpper.TokenHelper;
import nl.novi.serviceconnect.services.IServiceRequest;
import nl.novi.serviceconnect.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static nl.novi.serviceconnect.helpper.StringHelpers.getObjectResponseEntity;


@RestController
@RequestMapping("/servicesRequest")
public class ServiceRequestController {
    private final IServiceRequest service;
    private final JwtUtil jwtUtil;

    public ServiceRequestController(IServiceRequest service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<Object> createServiceRequest(@Valid @RequestBody ServiceRequestInputDto inputDto, HttpServletRequest request, BindingResult br) {

        if (br.hasFieldErrors()) {
            return getObjectResponseEntity(br);
        }
        else {
            String user = TokenHelper.getUserNameFromToken(request , jwtUtil);

            ServiceRequestOutputDto serviceRequest = service.createServiceRequest(inputDto, user);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + serviceRequest.id).toUriString());

            return ResponseEntity.created(uri).body(serviceRequest);
        }
    }

    @GetMapping
    public ResponseEntity<List<ServiceRequestOutputDto>> getAllServiceRequest() {
        return ResponseEntity.ok(service.getAllServiceRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestOutputDto> getServiceRequestById(@PathVariable Long id) {
        ServiceRequestOutputDto serviceRequestId = service.getServiceRequestById(id);
        return ResponseEntity.ok(serviceRequestId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceRequestOutputDto> updateService(@PathVariable Long id, @RequestBody ServiceRequestInputDto inputDto) {
        try {
            ServiceRequestOutputDto updatedServiceRequest = service.updateServiceRequest(id, inputDto);
            return ResponseEntity.ok(updatedServiceRequest);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceRequestInputDto> deleteServiceRequest(@PathVariable Long id) {
        try {
            service.deleteServiceRequest(id);
            return ResponseEntity.ok().build();
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
