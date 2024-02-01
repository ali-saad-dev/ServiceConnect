package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.CategoryServiceCountDto;

import java.util.List;

public interface IQueryService {
    List<CategoryServiceCountDto> countServicesPerCategory();
}
