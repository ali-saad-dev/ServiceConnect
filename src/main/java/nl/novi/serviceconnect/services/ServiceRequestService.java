package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceRequestInputDto;
import nl.novi.serviceconnect.dtos.ServiceRequestOutputDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.helpper.Mapper;
import nl.novi.serviceconnect.helpper.Helpers;
import nl.novi.serviceconnect.models.ServiceRequest;
import nl.novi.serviceconnect.models.Transaction;
import nl.novi.serviceconnect.repository.ServiceRepository;
import nl.novi.serviceconnect.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceRequestService implements IServiceRequest{
    private final ServiceRequestRepository repository;
    private final ServiceRepository serviceRepository;

    public ServiceRequestService(ServiceRequestRepository repository, ServiceRepository serviceRepository) {
        this.repository = repository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public ServiceRequestOutputDto createServiceRequest(ServiceRequestInputDto serviceRequestInputDto) {
        ServiceRequest serviceRequestResult =  repository.save(Mapper.fromDtoToServiceRequest(serviceRequestInputDto));

        nl.novi.serviceconnect.models.Service service = serviceRepository.findById(serviceRequestInputDto.getService().getId())
                .orElseThrow(() -> new RecordNotFoundException("Service not found with id: " + serviceRequestInputDto.getService().getId()));
        serviceRequestResult.setService(service);

        Transaction transaction = new Transaction();
        transaction.setTransactionDate(new Date());
        transaction.setPayed(false);

        serviceRequestResult.setTransaction(transaction);

        return Mapper.fromServiceRequestToDto(serviceRequestResult);
    }

    @Override
    public List<ServiceRequestOutputDto> getAllServiceRequests() {
        List<ServiceRequest> serviceRequestList = repository.findAll();
        List<ServiceRequestOutputDto> requestOutputDtos = new ArrayList<>();

        if (serviceRequestList.isEmpty()) {
            throw new RecordNotFoundException("No ServiceRequests found");
        }

        serviceRequestList.sort(Comparator.comparing(ServiceRequest::getId));

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

        if (Helpers.isNotNullOrEmpty(inputDto.getMessage())) {
            serviceRequest.setMessage(inputDto.getMessage());
        }
        if (Helpers.isNotNullOrEmpty(String.valueOf(inputDto.getState()))) {
            serviceRequest.setState(inputDto.getState());
        }
    }
    @Override
    public void deleteServiceRequest(Long id) {
        Optional<ServiceRequest> optionalServiceRequest = repository.findById(id);

        if (optionalServiceRequest.isEmpty()) throw new RecordNotFoundException("ServiceRequest with id " + id + " not found");

        repository.deleteById(id);
    }
}
