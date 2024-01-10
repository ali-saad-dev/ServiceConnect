package nl.novi.serviceconnect.helpper;

import nl.novi.serviceconnect.dtos.ServiceInputDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.models.Service;

public class Mapper {
    public static ServiceOutputDto fromServiceToDto(Service service){
        ServiceOutputDto dto = new ServiceOutputDto();
        dto.id = service.getId();
        dto.name = service.getName();
        dto.description = service.getDescription();
        dto.price = service.getPrice();
        dto.state = service.getState();
        return dto;
    }

    public static Service fromDtoToService(ServiceInputDto dto){
        Service service = new Service();
        service.setName(dto.name);
        service.setDescription(dto.description);
        service.setPrice(dto.price);
        service.setState(dto.state);
        return  service;
    }
}
