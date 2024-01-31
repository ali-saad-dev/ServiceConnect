package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceRequestInputDto;
import nl.novi.serviceconnect.dtos.ServiceRequestOutputDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.exceptions.UsernameNotFoundException;
import nl.novi.serviceconnect.helpper.Mapper;
import nl.novi.serviceconnect.helpper.Helpers;
import nl.novi.serviceconnect.models.ServiceRequest;
import nl.novi.serviceconnect.models.User;
import nl.novi.serviceconnect.repository.ServiceRepository;
import nl.novi.serviceconnect.repository.ServiceRequestRepository;
import nl.novi.serviceconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceRequestService implements IServiceRequest{
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
        ServiceRequest serviceRequestResult =  Mapper.fromDtoToServiceRequest(serviceRequestInputDto);

        nl.novi.serviceconnect.models.Service service = serviceRepository.findById(serviceRequestInputDto.getService().getId())
                .orElseThrow(() -> new RecordNotFoundException("Service not found with id: " + serviceRequestInputDto.getService().getId()));
        serviceRequestResult.setService(service);

        if (username != null && !username.isEmpty()) {
            User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found with username: " + username));
            serviceRequestResult.setUser(user);
        } else {
            throw new UsernameNotFoundException("Username is empty or null");
        }
        serviceRequestRepository.save(serviceRequestResult);

//        Transaction transaction = new Transaction();
//        transaction.setTransactionDate(new Date());
//        transaction.setPayed(false);
//        serviceRequestResult.setTransaction(transaction);


        return Mapper.fromServiceRequestToDto(serviceRequestResult);
    }

    @Override
    public List<ServiceRequestOutputDto> getAllServiceRequests() {
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
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
        Optional<ServiceRequest> serviceRequestOptional = serviceRequestRepository.findById(id);

        ServiceRequest serviceRequest = serviceRequestOptional.orElseThrow(() ->
                new RecordNotFoundException("No ServiceRequest found with id: " + id));

        return Mapper.fromServiceRequestToDto(serviceRequest);
    }
    @Override
    public ServiceRequestOutputDto updateServiceRequest(Long id, ServiceRequestInputDto inputDto) {
        ServiceRequest existingService = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("ServiceRequest with id: " + id + " not found"));

        updateServiceFields(existingService, inputDto);

        serviceRequestRepository.save(existingService);

        return Mapper.fromServiceRequestToDto(existingService);
    }

    private void updateServiceFields(ServiceRequest serviceRequest, ServiceRequestInputDto inputDto) {

        if (Helpers.isNotNullOrEmpty(inputDto.getMessage())) {
            serviceRequest.setMessage(inputDto.getMessage());
        }
        if ((inputDto.getState()!=null)) {
            serviceRequest.setState(inputDto.getState());
        }
    }
    @Override
    public void deleteServiceRequest(Long id) {
        Optional<ServiceRequest> optionalServiceRequest = serviceRequestRepository.findById(id);

        if (optionalServiceRequest.isEmpty()) throw new RecordNotFoundException("ServiceRequest with id " + id + " not found");

        serviceRequestRepository.deleteById(id);
    }
}
