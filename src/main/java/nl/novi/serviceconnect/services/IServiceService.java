package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceInputDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;

import java.util.List;

public interface IServiceService {
    ServiceInputDto createService(ServiceInputDto serviceInputDto);
    List<ServiceOutputDto> getAllService();

    ServiceOutputDto getServiceById(Long id);

    ServiceOutputDto updateService(Long id, ServiceInputDto serviceInputDto);

    void deleteService(Long id);
}

