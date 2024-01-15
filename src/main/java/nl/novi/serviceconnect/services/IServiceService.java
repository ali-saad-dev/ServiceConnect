package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceInputDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;

import java.util.List;

public interface IServiceService {
    ServiceInputDto CreateService(ServiceInputDto serviceInputDto);
    List<ServiceOutputDto> GetAllService();

    ServiceOutputDto GetServiceById(Long id);

    ServiceOutputDto UpdateService(Long id, ServiceInputDto serviceInputDto);
}

