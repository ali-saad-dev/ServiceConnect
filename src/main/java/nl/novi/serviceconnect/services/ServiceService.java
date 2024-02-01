package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceInputDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.exceptions.BadRequestException;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.exceptions.UsernameNotFoundException;
import nl.novi.serviceconnect.helpper.StringHelpers;
import nl.novi.serviceconnect.models.ServiceCategory;
import nl.novi.serviceconnect.models.User;
import nl.novi.serviceconnect.repository.ServiceCategoryRepository;
import nl.novi.serviceconnect.repository.ServiceRepository;
import nl.novi.serviceconnect.helpper.Mapper;
import nl.novi.serviceconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceService implements IServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceCategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ServiceService(ServiceRepository serviceRepo, ServiceCategoryRepository categoryRepository, UserRepository userRepository1) {
        this.serviceRepository = serviceRepo;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository1;
    }

    @Override
    public ServiceOutputDto createService(ServiceInputDto serviceInputDto, String username) {
        nl.novi.serviceconnect.models.Service service = Mapper.fromDtoToService(serviceInputDto);

        if (serviceInputDto.getCategory() != null && serviceInputDto.getCategory().getId() != null) {
            ServiceCategory category = categoryRepository.findById(serviceInputDto.getCategory().getId())
                    .orElseThrow(() -> new RecordNotFoundException("ServiceCategory not found with id: " + serviceInputDto.getCategory().getId()));

            if (username != null && !username.isEmpty()) {
                User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found with username: " + username));
                service.setUser(user);
            } else {
                throw new UsernameNotFoundException("Username is empty or null");
            }

            service.setCategory(category);
            serviceRepository.save(service);
            return Mapper.fromServiceToDto(service);
        } else {
            throw new BadRequestException("ServiceCategory or category ID is null in the input DTO");
        }
    }

    @Override
    public List<ServiceOutputDto> getAllService() {
        List<nl.novi.serviceconnect.models.Service> serviceList = serviceRepository.findAll();
        List<ServiceOutputDto> serviceOutputDtos = new ArrayList<>();

        if (serviceList.isEmpty()) {
            throw new RecordNotFoundException("No services found");
        }

        serviceList.sort(Comparator.comparing(nl.novi.serviceconnect.models.Service::getId));

        for(nl.novi.serviceconnect.models.Service service : serviceList) {
            serviceOutputDtos.add(Mapper.fromServiceToDto(service));
        }

        return serviceOutputDtos;
    }

    @Override
    public ServiceOutputDto getServiceById(Long id) {
        Optional<nl.novi.serviceconnect.models.Service> service = serviceRepository.findById(id);

        nl.novi.serviceconnect.models.Service result = service.orElseThrow(() ->
                new RecordNotFoundException("No service found with id: " + id));

        return Mapper.fromServiceToDto(result);
    }

    @Override
    public ServiceOutputDto updateService(Long id, ServiceInputDto serviceInputDto) {
        nl.novi.serviceconnect.models.Service existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Service with id: " + id + " not found"));
       nl.novi.serviceconnect.models.Service result =  updateServiceFields(existingService, serviceInputDto);
        serviceRepository.save(result);

        return Mapper.fromServiceToDto(result);
    }

    private nl.novi.serviceconnect.models.Service updateServiceFields(nl.novi.serviceconnect.models.Service service, ServiceInputDto serviceInputDto) {

        return new nl.novi.serviceconnect.models.Service (
                service.getId(),
                StringHelpers.isNotNullOrEmpty(serviceInputDto.getName()) ? serviceInputDto.getName() : service.getName(),
                StringHelpers.isNotNullOrEmpty(serviceInputDto.getDescription()) ? serviceInputDto.getDescription() : service.getDescription(),
                (serviceInputDto.getPrice() != null && serviceInputDto.getPrice() != 0.0) ? serviceInputDto.getPrice() : service.getPrice(),
                StringHelpers.isNotNullOrEmpty(String.valueOf(serviceInputDto.getState())) ? serviceInputDto.getState() : service.getState(),
                serviceInputDto.getCategory() != null ? serviceInputDto.getCategory() : service.getCategory()
        );
    }
    @Override
    public void deleteService(Long id) {
        Optional<nl.novi.serviceconnect.models.Service> optionalService = serviceRepository.findById(id);

        if (optionalService.isEmpty()) {
            throw new RecordNotFoundException("Service with id " + id + " not found");
        } else {
            serviceRepository.deleteById(id);
        }
    }
}
