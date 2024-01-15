package nl.novi.serviceconnect.controllers;

import jakarta.validation.Valid;
import nl.novi.serviceconnect.dtos.ServiceInputDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.dtos.ServiceRequestInputDto;
import nl.novi.serviceconnect.dtos.ServiceRequestOutputDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.services.ServiceRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/servicesRequest")
public class ServiceRequestController {
    private final ServiceRequestService service;

    public ServiceRequestController(ServiceRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> createServiceRequest(@Valid @RequestBody ServiceRequestInputDto inputDto, BindingResult br) {

        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField());
                sb.append(" : ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        else {
            ServiceRequestInputDto ServiceRequestInputDto = service.createServiceRequest(inputDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + ServiceRequestInputDto.id).toUriString());

            return ResponseEntity.created(uri).body(ServiceRequestInputDto);
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
