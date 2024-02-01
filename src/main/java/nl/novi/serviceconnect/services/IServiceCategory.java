package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceCategoryInputDto;
import nl.novi.serviceconnect.dtos.ServiceCategoryOutputDto;

import java.util.List;

public interface IServiceCategory {
    ServiceCategoryOutputDto createServiceCategory(ServiceCategoryInputDto inputDto);

    List<ServiceCategoryOutputDto> getAllCategories();

    ServiceCategoryOutputDto getCategoryById(Long id);

    ServiceCategoryOutputDto updateCategory(Long id, ServiceCategoryInputDto inputDto);

    void deleteCategory(Long id);
}
