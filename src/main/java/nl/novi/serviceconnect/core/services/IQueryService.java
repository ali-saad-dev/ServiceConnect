package nl.novi.serviceconnect.core.services;

import nl.novi.serviceconnect.core.dtos.CategoryServiceCountDto;
import nl.novi.serviceconnect.core.dtos.ServiceOutputDto;

import java.util.List;

public interface IQueryService {
    List<CategoryServiceCountDto> countServicesPerCategory();

    List<ServiceOutputDto> searchServicesByPrice(Long categoryId, Double minPrice, Double maxPrice);
}
