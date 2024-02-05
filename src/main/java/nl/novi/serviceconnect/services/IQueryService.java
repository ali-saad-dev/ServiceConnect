package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.CategoryServiceCountDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;

import java.util.List;

public interface IQueryService {
    List<CategoryServiceCountDto> countServicesPerCategory();

    List<ServiceOutputDto> searchServicesByPrice(Long categoryId, Double minPrice, Double maxPrice);
}
