package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceInputDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.repository.ServiceRepository;
import nl.novi.serviceconnect.helpper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


}
