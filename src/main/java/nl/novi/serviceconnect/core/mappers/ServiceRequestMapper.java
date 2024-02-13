package nl.novi.serviceconnect.core.mappers;

import nl.novi.serviceconnect.core.dtos.ServiceRequestInputDto;
import nl.novi.serviceconnect.core.dtos.ServiceRequestOutputDto;
import nl.novi.serviceconnect.infrastructure.models.ServiceRequest;

public class ServiceRequestMapper {

    public static ServiceRequestOutputDto fromServiceRequestToDto(ServiceRequest serviceRequest){
        ServiceRequestOutputDto dto = new ServiceRequestOutputDto();
        dto.id = serviceRequest.getId();
        dto.username = serviceRequest.getUser() != null ? serviceRequest.getUser().getUsername() : null;
        dto.message = serviceRequest.getMessage();
        dto.requestState = serviceRequest.getState();
        dto.service = ServiceMapper.fromServiceToDto(serviceRequest.getService());
        return dto;
    }

    public static ServiceRequest fromDtoToServiceRequest(ServiceRequestInputDto inputDto){
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setMessage(inputDto.getMessage());
        serviceRequest.setState(inputDto.getState());
        serviceRequest.setService(inputDto.getService());
        return  serviceRequest;
    }
}
