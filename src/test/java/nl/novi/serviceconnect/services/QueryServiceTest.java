package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.CategoryServiceCountDto;
import nl.novi.serviceconnect.dtos.ServiceOutputDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.models.Service;
import nl.novi.serviceconnect.models.ServiceState;
import nl.novi.serviceconnect.repository.CategoryRepository;
import nl.novi.serviceconnect.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QueryServiceTest {

    @Mock
    ServiceRepository serviceRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    QueryService queryService;

    @Test
    public void countServicesPerCategory() {
        //Arrange
        List<Object[]> mockResult = new ArrayList<>();
        mockResult.add(new Object[]{"Category1", 5L});
        mockResult.add(new Object[]{"Category2", 3L});
        mockResult.add(new Object[]{"Category3", 7L});
        when(serviceRepository.countServicesPerCategory()).thenReturn(mockResult);

        //Act
        List<CategoryServiceCountDto> result = queryService.countServicesPerCategory();

        //Assert
        assertNotNull(result);
        assertEquals(3, result.size());

        assertEquals("Category1", result.get(0).getCategoryName());
        assertEquals(5L, result.get(0).getNumberOfServices());

        assertEquals("Category2", result.get(1).getCategoryName());
        assertEquals(3L, result.get(1).getNumberOfServices());

        assertEquals("Category3", result.get(2).getCategoryName());
        assertEquals(7L, result.get(2).getNumberOfServices());
    }

    @Test
    public void shouldSearchServicesByPrice() {
        //Arrange
        Long categoryId = 1L;
        Double minPrice = 50.0;
        Double maxPrice = 100.0;

        List<Service> mockServiceList = new ArrayList<>();
        mockServiceList.add(new Service(1L,"Service1","test description",60.0, ServiceState.Available, categoryRepository.getReferenceById(1L)));
        mockServiceList.add(new Service(2L,"Service2","test description",80.0, ServiceState.Available, categoryRepository.getReferenceById(2L)));
        when(serviceRepository.searchServicesByPrice(categoryId, minPrice, maxPrice)).thenReturn(mockServiceList);

        //Act
        List<ServiceOutputDto> result = queryService.searchServicesByPrice(categoryId, minPrice, maxPrice);

        //Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Service1", result.get(0).name);
        assertEquals(60.0, result.get(0).price);
        assertEquals("Service2", result.get(1).name);
        assertEquals(80.0, result.get(1).price);
    }

    @Test
    public void SearchServicesByPrice_NoServicesFound() {
        //Arrange
        Long categoryId = 1L;
        Double minPrice = 50.0;
        Double maxPrice = 100.0;
        when(serviceRepository.searchServicesByPrice(categoryId, minPrice, maxPrice)).thenReturn(new ArrayList<>());

        //Act & Assert
        assertThrows(RecordNotFoundException.class, () -> queryService.searchServicesByPrice(categoryId, minPrice, maxPrice));
    }
}