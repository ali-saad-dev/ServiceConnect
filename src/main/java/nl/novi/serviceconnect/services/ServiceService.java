package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceInputDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.helpper.StringHelpers;
import nl.novi.serviceconnect.models.ServiceCategory;
import nl.novi.serviceconnect.repository.ServiceCategoryRepository;
import nl.novi.serviceconnect.repository.ServiceRepository;
import nl.novi.serviceconnect.helpper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceService implements IServiceService {
    private final ServiceRepository repository;
    private final ServiceCategoryRepository categoryRepository;

    public ServiceService(ServiceRepository repo, ServiceCategoryRepository categoryRepository) {
        this.repository = repo;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public ServiceOutputDto createService(ServiceInputDto serviceInputDto) {
        nl.novi.serviceconnect.models.Service service = Mapper.fromDtoToService(serviceInputDto);

       ServiceCategory category = categoryRepository.findById(serviceInputDto.getServiceCategory().getId())
                .orElseThrow(() -> new RecordNotFoundException("ServiceCategory not found with id: " + serviceInputDto.getServiceCategory().getId()));

        service.setCategory(category);
        repository.save(service);
        return Mapper.fromServiceToDto(service);
    }

    @Override
    public List<ServiceOutputDto> getAllService() {
        List<nl.novi.serviceconnect.models.Service> serviceList = repository.findAll();
        List<ServiceOutputDto> serviceOutputDtos = new ArrayList<>();

        if (serviceList.isEmpty()) {
            throw new RecordNotFoundException("No services found");
        }

        for(nl.novi.serviceconnect.models.Service service : serviceList) {
            serviceOutputDtos.add(Mapper.fromServiceToDto(service));
        }

        return serviceOutputDtos;
    }

    @Override
    public ServiceOutputDto getServiceById(Long id) {
        Optional<nl.novi.serviceconnect.models.Service> service = repository.findById(id);

        nl.novi.serviceconnect.models.Service result = service.orElseThrow(() ->
                new RecordNotFoundException("No service found with id: " + id));

        return Mapper.fromServiceToDto(result);
    }

    @Override
    public ServiceOutputDto updateService(Long id, ServiceInputDto serviceInputDto) {
        nl.novi.serviceconnect.models.Service existingService = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Service with id: " + id + " not found"));
       nl.novi.serviceconnect.models.Service result =  updateServiceFields(existingService, serviceInputDto);
        repository.save(result);

        return Mapper.fromServiceToDto(result);
    }

    private nl.novi.serviceconnect.models.Service updateServiceFields(nl.novi.serviceconnect.models.Service service, ServiceInputDto serviceInputDto) {

        return new nl.novi.serviceconnect.models.Service(
                service.getId(),
                StringHelpers.isNotNullOrEmpty(serviceInputDto.getName()) ? serviceInputDto.getName() : service.getName(),
                StringHelpers.isNotNullOrEmpty(serviceInputDto.getDescription()) ? serviceInputDto.getDescription() : service.getDescription(),
                (serviceInputDto.getPrice() != null && serviceInputDto.getPrice() != 0.0) ? serviceInputDto.getPrice() : service.getPrice(),
                StringHelpers.isNotNullOrEmpty(String.valueOf(serviceInputDto.getState())) ? serviceInputDto.getState() : service.getState()
        );
    }
    @Override
    public void deleteService(Long id) {
        Optional<nl.novi.serviceconnect.models.Service> optionalService = repository.findById(id);

        if (optionalService.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Service with id " + id + " not found");
        }
    }
}
