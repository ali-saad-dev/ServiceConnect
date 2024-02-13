package nl.novi.serviceconnect.core.services;

import nl.novi.serviceconnect.core.dtos.CategoryServiceCountDto;
import nl.novi.serviceconnect.core.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.core.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.core.mappers.ServiceMapper;
import nl.novi.serviceconnect.infrastructure.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class QueryService implements IQueryService {

    private final ServiceRepository serviceRepository;

    public QueryService(ServiceRepository serviceRepository) { this.serviceRepository = serviceRepository; }

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

    @Override
    public List<ServiceOutputDto> searchServicesByPrice(Long categoryId, Double minPrice, Double maxPrice) {
        List<nl.novi.serviceconnect.infrastructure.models.Service> serviceList = serviceRepository.searchServicesByPrice(categoryId, minPrice, maxPrice);
        List<ServiceOutputDto> serviceOutputDto = new ArrayList<>();

        if (serviceList.isEmpty()) {
            throw new RecordNotFoundException("No services found matching the search criteria");
        }

        serviceList.sort(Comparator.comparing(nl.novi.serviceconnect.infrastructure.models.Service::getId));

        for (nl.novi.serviceconnect.infrastructure.models.Service service : serviceList) {
            serviceOutputDto.add(ServiceMapper.fromServiceToDto(service));
        }

        return serviceOutputDto;
    }
}
