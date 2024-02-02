package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.CategoryServiceCountDto;
import nl.novi.serviceconnect.repository.CategoryRepository;
import nl.novi.serviceconnect.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryService implements IQueryService {

    private final ServiceRepository serviceRepository;

    private final CategoryRepository categoryRepository;

    public QueryService(ServiceRepository serviceRepository, CategoryRepository categoryRepository) {
        this.serviceRepository = serviceRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryServiceCountDto> countServicesPerCategory() {
        List<Object[]> result = serviceRepository.countServicesPerCategory();

        List<CategoryServiceCountDto> resultList = new ArrayList<>();

        for (Object[] row : result) {
            String categoryName = (String) row[0];
            Long numberOfServices = (Long) row[1];

            CategoryServiceCountDto countDto = new CategoryServiceCountDto(categoryName, numberOfServices);
            resultList.add(countDto);
        }

        return resultList;
    }
}