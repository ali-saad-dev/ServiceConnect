package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceInputDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;

import java.util.List;

public interface IServiceService {

    ServiceOutputDto createService(ServiceInputDto serviceInputDto, String username);

    List<ServiceOutputDto> getAllService();

    ServiceOutputDto getServiceById(Long id);

    ServiceOutputDto updateService(Long id, ServiceInputDto serviceInputDto);

    void deleteService(Long id);
}

