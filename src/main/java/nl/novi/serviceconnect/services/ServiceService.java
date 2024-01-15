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
    private final ServiceRepository repo;

    public ServiceService(ServiceRepository repo) {
        this.repo = repo;
    }

    @Override
    public ServiceInputDto CreateService(ServiceInputDto serviceInputDto) {
        repo.save(Mapper.fromDtoToService(serviceInputDto));
        return serviceInputDto;
    }

    @Override
    public List<ServiceOutputDto> GetAllService() {
        List<nl.novi.serviceconnect.models.Service> serviceList = repo.findAll();
        List<ServiceOutputDto> serviceOutputDtos = new ArrayList<>();

        for(nl.novi.serviceconnect.models.Service service : serviceList) {
            serviceOutputDtos.add(Mapper.fromServiceToDto(service));
        }
        return serviceOutputDtos;
    }

    @Override
    public ServiceOutputDto GetServiceById(Long id) {

        Optional<nl.novi.serviceconnect.models.Service> service = repo.findById(id);

        if(service.isPresent()){
            return Mapper.fromServiceToDto(service.get());
        }
        else {
            throw new RecordNotFoundException("No service founded");
        }
    }

    @Override
    public ServiceOutputDto UpdateService(Long id, ServiceInputDto serviceInputDto) {
        Optional<nl.novi.serviceconnect.models.Service> optionalService = repo.findById(id);

        if (optionalService.isPresent()) {
            nl.novi.serviceconnect.models.Service existingService = optionalService.get();
            existingService.setName(serviceInputDto.name);
            existingService.setPrice(serviceInputDto.price);
            existingService.setDescription(serviceInputDto.description);
            existingService.setState(serviceInputDto.state);

            repo.save(existingService);

            return Mapper.fromServiceToDto(existingService);
        } else {
            throw new RecordNotFoundException("Service with id: " + id + " not found");
        }
    }
}
