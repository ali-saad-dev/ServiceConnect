package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceInputDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;

import java.util.List;

public interface IServiceService {
    ServiceInputDto createService(ServiceInputDto serviceInputDto);
    List<ServiceOutputDto> GetAllService();
}
