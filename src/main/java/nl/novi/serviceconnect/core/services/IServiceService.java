package nl.novi.serviceconnect.core.services;

import nl.novi.serviceconnect.core.dtos.ServiceInputDto;
import nl.novi.serviceconnect.core.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.core.dtos.ServiceRequestInputDto;
import nl.novi.serviceconnect.core.dtos.ServiceRequestOutputDto;

import java.util.List;

public interface IServiceService {

    ServiceOutputDto createService(ServiceInputDto serviceInputDto, String username);

    List<ServiceOutputDto> getAllService();

    ServiceOutputDto getServiceById(Long id);

    ServiceOutputDto updateService(Long id, ServiceInputDto serviceInputDto);

    void deleteService(Long id);

    ServiceRequestOutputDto acceptServiceRequest(ServiceRequestInputDto serviceRequestInputDto, Long id, String username);
}

