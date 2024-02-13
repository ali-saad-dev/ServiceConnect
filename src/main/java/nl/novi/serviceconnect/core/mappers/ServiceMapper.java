package nl.novi.serviceconnect.core.mappers;

import nl.novi.serviceconnect.core.dtos.ServiceInputDto;
import nl.novi.serviceconnect.core.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.infrastructure.models.Service;

public class ServiceMapper {

    public static ServiceOutputDto fromServiceToDto(Service service){
        ServiceOutputDto dto = new ServiceOutputDto();
        dto.id = service.getId();
        dto.name = service.getName();
        dto.description = service.getDescription();
        dto.price = service.getPrice();
        dto.state = service.getState();
        dto.serviceCategory = service.getCategory();
        dto.username = service.getUser() != null ? service.getUser().getUsername() : null;
        return dto;
    }

    public static Service fromDtoToService(ServiceInputDto dto){
        Service service = new Service();
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setPrice(dto.getPrice());
        service.setState(dto.getState());
        service.setCategory(dto.getCategory());
        return  service;
    }
}
