package nl.novi.serviceconnect.core.mappers;

import nl.novi.serviceconnect.core.dtos.ServiceCategoryInputDto;
import nl.novi.serviceconnect.core.dtos.ServiceCategoryOutputDto;
import nl.novi.serviceconnect.infrastructure.models.ServiceCategory;

public class CategoryMapper {

    public static ServiceCategoryOutputDto fromCategoryToDto(ServiceCategory category){
        ServiceCategoryOutputDto dto = new ServiceCategoryOutputDto();
        dto.id = category.getId();
        dto.name = category.getName();
        dto.description = category.getDescription();
        return dto;
    }

    public static ServiceCategory fromDtoToCategory(ServiceCategoryInputDto dto){
        ServiceCategory category = new ServiceCategory();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return  category;
    }
}
