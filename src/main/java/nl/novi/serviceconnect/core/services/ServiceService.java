package nl.novi.serviceconnect.core.services;

import nl.novi.serviceconnect.core.dtos.ServiceInputDto;
import nl.novi.serviceconnect.core.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.core.dtos.ServiceRequestInputDto;
import nl.novi.serviceconnect.core.dtos.ServiceRequestOutputDto;
import nl.novi.serviceconnect.core.exceptions.BadRequestException;
import nl.novi.serviceconnect.core.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.core.exceptions.UsernameNotFoundException;
import nl.novi.serviceconnect.core.helpers.StringHelpers;
import nl.novi.serviceconnect.core.mappers.ServiceMapper;
import nl.novi.serviceconnect.core.mappers.ServiceRequestMapper;
import nl.novi.serviceconnect.infrastructure.models.ServiceCategory;
import nl.novi.serviceconnect.infrastructure.models.ServiceRequest;
import nl.novi.serviceconnect.infrastructure.models.User;
import nl.novi.serviceconnect.infrastructure.repository.CategoryRepository;
import nl.novi.serviceconnect.infrastructure.repository.ServiceRepository;
import nl.novi.serviceconnect.infrastructure.repository.ServiceRequestRepository;
import nl.novi.serviceconnect.infrastructure.repository.UserRepository;
import nl.novi.serviceconnect.infrastructure.models.RequestState;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceService implements IServiceService {

    private final ServiceRepository serviceRepository;
    private final CategoryRepository categoryRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;

    public ServiceService(ServiceRepository serviceRepo, CategoryRepository categoryRepository, ServiceRequestRepository serviceRequestRepository, UserRepository userRepository1) {
        this.serviceRepository = serviceRepo;
        this.categoryRepository = categoryRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.userRepository = userRepository1;
    }

    @Override
    public ServiceOutputDto createService(ServiceInputDto serviceInputDto, String username) {

        nl.novi.serviceconnect.infrastructure.models.Service service = ServiceMapper.fromDtoToService(serviceInputDto);

        if (username == null || username.isEmpty()) throw new UsernameNotFoundException("Username is empty or null");

        if (serviceInputDto.getCategory() == null || serviceInputDto.getCategory().getId() == null)
            throw new RecordNotFoundException("ServiceCategory or category Id is null in the input DTO");

        ServiceCategory category = categoryRepository.findById(serviceInputDto.getCategory().getId())
                .orElseThrow(() -> new RecordNotFoundException("ServiceCategory not found with id: " + serviceInputDto.getCategory().getId()));

        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found with username: " + username));
        service.setUser(user);

        service.setCategory(category);
        serviceRepository.save(service);
        return ServiceMapper.fromServiceToDto(service);
    }

    @Override
    public List<ServiceOutputDto> getAllService() {

        List<nl.novi.serviceconnect.infrastructure.models.Service> serviceList = serviceRepository.findAll();
        List<ServiceOutputDto> serviceOutputDto = new ArrayList<>();

        if (serviceList.isEmpty()) {
            throw new RecordNotFoundException("No services found");
        }

        serviceList.sort(Comparator.comparing(nl.novi.serviceconnect.infrastructure.models.Service::getId));

        for(nl.novi.serviceconnect.infrastructure.models.Service service : serviceList) {
            serviceOutputDto.add(ServiceMapper.fromServiceToDto(service));
        }

        return serviceOutputDto;
    }

    @Override
    public ServiceOutputDto getServiceById(Long id) {
        Optional<nl.novi.serviceconnect.infrastructure.models.Service> service = serviceRepository.findById(id);

        nl.novi.serviceconnect.infrastructure.models.Service result = service.orElseThrow(() ->
                new RecordNotFoundException("No service found with id: " + id));

        return ServiceMapper.fromServiceToDto(result);
    }

    @Override
    public ServiceOutputDto updateService(Long id, ServiceInputDto serviceInputDto) {
        nl.novi.serviceconnect.infrastructure.models.Service existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Service with id: " + id + " not found"));
       nl.novi.serviceconnect.infrastructure.models.Service result =  updateServiceFields(existingService, serviceInputDto);
        serviceRepository.save(result);

        return ServiceMapper.fromServiceToDto(result);
    }

    @Override
    public void deleteService(Long id) {

        Optional<nl.novi.serviceconnect.infrastructure.models.Service> optionalService = serviceRepository.findById(id);

        if (optionalService.isEmpty()) throw new RecordNotFoundException("Service with id " + id + " not found");

        serviceRepository.deleteById(id);
    }

    @Override
    public ServiceRequestOutputDto acceptServiceRequest(ServiceRequestInputDto serviceRequestInputDto, Long id, String username) {

        ServiceRequest serviceRequestResult = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("ServiceRequest not found with id: " + id));

        boolean isUsernameInServiceRequest = serviceRequestResult.getUser().getUsername().equals(username);

        boolean isUsernameInService = serviceRequestResult.getService().getUser().getUsername().equals(username);

        boolean isNewStateApprovedOrRejected = serviceRequestInputDto.getState() == RequestState.Approved
                || serviceRequestInputDto.getState() == RequestState.Rejected || serviceRequestInputDto.getState() == RequestState.Completed;

        boolean isNewStateDeleted = serviceRequestInputDto.getState() == RequestState.Deleted;

        if (!(isUsernameInService && isNewStateApprovedOrRejected && !isUsernameInServiceRequest)
                && !(isUsernameInServiceRequest && isNewStateDeleted && !isUsernameInService))
            throw new BadRequestException("Not authorized to approve service request");

        serviceRequestResult.setState(serviceRequestInputDto.getState());
        serviceRequestResult.setMessage(serviceRequestInputDto.getMessage());

        serviceRequestRepository.save(serviceRequestResult);
        return ServiceRequestMapper.fromServiceRequestToDto(serviceRequestResult);
    }

    private nl.novi.serviceconnect.infrastructure.models.Service updateServiceFields(nl.novi.serviceconnect.infrastructure.models.Service service, ServiceInputDto serviceInputDto) {

        return new nl.novi.serviceconnect.infrastructure.models.Service (
                service.getId(),
                StringHelpers.isNotNullOrEmpty(serviceInputDto.getName()) ? serviceInputDto.getName() : service.getName(),
                StringHelpers.isNotNullOrEmpty(serviceInputDto.getDescription()) ? serviceInputDto.getDescription() : service.getDescription(),
                (serviceInputDto.getPrice() != null && serviceInputDto.getPrice() != 0.0) ? serviceInputDto.getPrice() : service.getPrice(),
                StringHelpers.isNotNullOrEmpty(String.valueOf(serviceInputDto.getState())) ? serviceInputDto.getState() : service.getState(),
                serviceInputDto.getCategory() != null ? serviceInputDto.getCategory() : service.getCategory()
        );
    }
}
