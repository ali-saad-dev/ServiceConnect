package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceInputDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.dtos.ServiceRequestInputDto;
import nl.novi.serviceconnect.dtos.ServiceRequestOutputDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.helpper.Mapper;
import nl.novi.serviceconnect.models.ServiceRequest;
import nl.novi.serviceconnect.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestService implements IServiceRequest{
    private final ServiceRequestRepository repository;

    public ServiceRequestService(ServiceRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceRequestInputDto createServiceRequest(ServiceRequestInputDto serviceRequestInputDto) {
        repository.save(Mapper.fromDtoToServiceRequest(serviceRequestInputDto));
        return serviceRequestInputDto;
    }

    @Override
    public List<ServiceRequestOutputDto> getAllServiceRequests() {
        List<ServiceRequest> serviceRequestList = repository.findAll();
        List<ServiceRequestOutputDto> requestOutputDtos = new ArrayList<>();

        if (serviceRequestList.isEmpty()) {
            throw new RecordNotFoundException("No ServiceRequests found");
        }

        for(ServiceRequest serviceRequest : serviceRequestList) {
            requestOutputDtos.add(Mapper.fromServiceRequestToDto(serviceRequest));
        }

        return requestOutputDtos;
    }

    @Override
    public ServiceRequestOutputDto getServiceRequestById(Long id) {
        Optional<ServiceRequest> serviceRequestOptional = repository.findById(id);

        ServiceRequest serviceRequest = serviceRequestOptional.orElseThrow(() ->
                new RecordNotFoundException("No ServiceRequest found with id: " + id));

        return Mapper.fromServiceRequestToDto(serviceRequest);
    }
    @Override
    public ServiceRequestOutputDto updateServiceRequest(Long id, ServiceRequestInputDto inputDto) {
        ServiceRequest existingService = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("ServiceRequest with id: " + id + " not found"));

        updateServiceFields(existingService, inputDto);

        repository.save(existingService);

        return Mapper.fromServiceRequestToDto(existingService);
    }

    private void updateServiceFields(ServiceRequest serviceRequest, ServiceRequestInputDto inputDto) {
        // Only update fields that are not null or empty in the input DTO
        if (isNotNullOrEmpty(inputDto.getMessage())) {
            serviceRequest.setMessage(inputDto.getMessage());
        }
        if (isNotNullOrEmpty(String.valueOf(inputDto.getState()))) {
            serviceRequest.setState(inputDto.getState());
        }
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    @Override
    public void deleteServiceRequest(Long id) {
        Optional<ServiceRequest> optionalServiceRequest = repository.findById(id);

        if (optionalServiceRequest.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("ServiceRequest with id " + id + " not found");
        }
    }
}
