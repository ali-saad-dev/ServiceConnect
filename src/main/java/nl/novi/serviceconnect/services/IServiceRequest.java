package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceRequestInputDto;
import nl.novi.serviceconnect.dtos.ServiceRequestOutputDto;

import java.util.List;

public interface IServiceRequest {

    ServiceRequestOutputDto createServiceRequest(ServiceRequestInputDto serviceRequestInputDto, String username);

    List<ServiceRequestOutputDto> getAllServiceRequests();

    ServiceRequestOutputDto getServiceRequestById(Long id);

    ServiceRequestOutputDto updateServiceRequest(Long id, ServiceRequestInputDto serviceRequestInputDto);

    void deleteServiceRequest(Long id);
}
