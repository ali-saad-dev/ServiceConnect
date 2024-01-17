package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceInputDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.repository.ServiceRepository;
import nl.novi.serviceconnect.helpper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceService implements IServiceService {
    private final ServiceRepository repository;

    public ServiceService(ServiceRepository repo) {
        this.repository = repo;
    }

    @Override
    public ServiceInputDto createService(ServiceInputDto serviceInputDto) {
        repository.save(Mapper.fromDtoToService(serviceInputDto));
        return serviceInputDto;
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
        Optional<nl.novi.serviceconnect.models.Service> serviceOptional = repository.findById(id);

        nl.novi.serviceconnect.models.Service service = serviceOptional.orElseThrow(() ->
                new RecordNotFoundException("No service found with id: " + id));

        return Mapper.fromServiceToDto(service);
    }

    @Override
    public ServiceOutputDto updateService(Long id, ServiceInputDto serviceInputDto) {
        nl.novi.serviceconnect.models.Service existingService = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Service with id: " + id + " not found"));

        updateServiceFields(existingService, serviceInputDto);

        repository.save(existingService);

        return Mapper.fromServiceToDto(existingService);
    }

    private void updateServiceFields(nl.novi.serviceconnect.models.Service service, ServiceInputDto serviceInputDto) {
        // Only update fields that are not null or empty in the input DTO
        if (isNotNullOrEmpty(serviceInputDto.getName())) {
            service.setName(serviceInputDto.getName());
        }
        if (isNotNullOrEmpty(serviceInputDto.getDescription())) {
            service.setDescription(serviceInputDto.getDescription());
        }
        if (isNotNullOrEmpty(String.valueOf(serviceInputDto.getState()))) {
            service.setState(serviceInputDto.getState());
        }
        // Check for null explicitly for Double and non-zero for price
        if (serviceInputDto.getPrice() != null && serviceInputDto.getPrice() != 0.0) {
            service.setPrice(serviceInputDto.getPrice());
        }
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
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
