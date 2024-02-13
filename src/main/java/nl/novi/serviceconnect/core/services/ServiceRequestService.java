package nl.novi.serviceconnect.core.services;

import nl.novi.serviceconnect.core.dtos.ServiceRequestInputDto;
import nl.novi.serviceconnect.core.dtos.ServiceRequestOutputDto;
import nl.novi.serviceconnect.core.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.core.exceptions.UsernameNotFoundException;
import nl.novi.serviceconnect.core.helpers.Helpers;
import nl.novi.serviceconnect.core.mappers.ServiceRequestMapper;
import nl.novi.serviceconnect.infrastructure.models.ServiceRequest;
import nl.novi.serviceconnect.infrastructure.models.User;
import nl.novi.serviceconnect.infrastructure.repository.ServiceRepository;
import nl.novi.serviceconnect.infrastructure.repository.ServiceRequestRepository;
import nl.novi.serviceconnect.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceRequestService implements IServiceRequest {

    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public ServiceRequestService(ServiceRequestRepository repository, ServiceRepository serviceRepository, UserRepository userRepository) {
        this.serviceRequestRepository = repository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ServiceRequestOutputDto createServiceRequest(ServiceRequestInputDto serviceRequestInputDto, String username) {
        ServiceRequest serviceRequestResult =  ServiceRequestMapper.fromDtoToServiceRequest(serviceRequestInputDto);

        if (username == null || username.isEmpty()) throw new UsernameNotFoundException("Username is empty or null");

        nl.novi.serviceconnect.infrastructure.models.Service service = serviceRepository.findById(serviceRequestInputDto.getService().getId())
                .orElseThrow(() -> new RecordNotFoundException("Service not found with id: " + serviceRequestInputDto.getService().getId()));
        serviceRequestResult.setService(service);

        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found with username: " + username));
        serviceRequestResult.setUser(user);

        serviceRequestRepository.save(serviceRequestResult);

        return ServiceRequestMapper.fromServiceRequestToDto(serviceRequestResult);
    }

    @Override
    public List<ServiceRequestOutputDto> getAllServiceRequests() {
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        List<ServiceRequestOutputDto> requestOutputDto = new ArrayList<>();

        if (serviceRequestList.isEmpty()) {
            throw new RecordNotFoundException("No ServiceRequests found");
        }

        serviceRequestList.sort(Comparator.comparing(ServiceRequest::getId));

        for(ServiceRequest serviceRequest : serviceRequestList) {
            requestOutputDto.add(ServiceRequestMapper.fromServiceRequestToDto(serviceRequest));
        }

        return requestOutputDto;
    }

    @Override
    public ServiceRequestOutputDto getServiceRequestById(Long id) {
        Optional<ServiceRequest> serviceRequestOptional = serviceRequestRepository.findById(id);

        ServiceRequest serviceRequest = serviceRequestOptional.orElseThrow(() ->
                new RecordNotFoundException(" No ServiceRequest found with id: " + id));

        return ServiceRequestMapper.fromServiceRequestToDto(serviceRequest);
    }

    @Override
    public ServiceRequestOutputDto updateServiceRequest(Long id, ServiceRequestInputDto inputDto) {
        ServiceRequest existingService = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("ServiceRequest with id: " + id + " not found"));

        updateServiceFields(existingService, inputDto);

        serviceRequestRepository.save(existingService);

        return ServiceRequestMapper.fromServiceRequestToDto(existingService);
    }

    @Override
    public void deleteServiceRequest(Long id) {
        Optional<ServiceRequest> optionalServiceRequest = serviceRequestRepository.findById(id);

        if (optionalServiceRequest.isEmpty()) throw new RecordNotFoundException("ServiceRequest with id " + id + " not found");

        serviceRequestRepository.deleteById(id);
    }

    private void updateServiceFields(ServiceRequest serviceRequest, ServiceRequestInputDto inputDto) {

        if (Helpers.isNotNullOrEmpty(inputDto.getMessage())) {
            serviceRequest.setMessage(inputDto.getMessage());
        }
        if ((inputDto.getState()!=null)) {
            serviceRequest.setState(inputDto.getState());
        }
    }
}
