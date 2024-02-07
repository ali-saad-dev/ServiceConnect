package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceCategoryInputDto;
import nl.novi.serviceconnect.dtos.ServiceCategoryOutputDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.models.ServiceCategory;
import nl.novi.serviceconnect.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    @Test
    public void shouldCreateServiceCategory() {

        //Arrange
        ServiceCategoryInputDto inputDto = new ServiceCategoryInputDto("TestCategory", "Test Description" );

        //Act
        ServiceCategoryOutputDto outputDto = categoryService.createServiceCategory(inputDto);

        //Assert
        assertNotNull(outputDto);
        assertEquals("TestCategory", outputDto.name);
        assertEquals("Test Description", outputDto.description);
    }

    @Test
    public void shouldGetAllCategories() {

        //Arrange
        ServiceCategory mockCategory = new ServiceCategory("TestCategory", "Test Description");

        //Act
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(mockCategory));
        List<ServiceCategoryOutputDto> outputDtoList = categoryService.getAllCategories();

        //Assert
        assertEquals(1, outputDtoList.size());
        assertEquals("TestCategory", outputDtoList.get(0).name);
        assertEquals("Test Description", outputDtoList.get(0).description);
    }

    @Test
    public void whenNOCategoriesReturnNoCategoriesFound() {

        //Act
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        //Assert
        assertThrows(RecordNotFoundException.class, () -> categoryService.getAllCategories());
    }

    @Test
    public void shouldGetCategoryById() {

        //Arrange
        ServiceCategory mockCategory = new ServiceCategory("TestCategory", "Test Description");

        //Act
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));
        ServiceCategoryOutputDto outputDto = categoryService.getCategoryById(1L);

        //Assert
        assertNotNull(outputDto);
        assertEquals("TestCategory", outputDto.name);
        assertEquals("Test Description", outputDto.description);
    }

    @Test
    public void whenNoCategoryFoundShouldReturn_CategoryNotFound() {

        //Act
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        //Assert
        assertThrows(RecordNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    public void shouldUpdateCategory() {

        //Arrange
        ServiceCategoryInputDto inputDto = new ServiceCategoryInputDto("UpdatedTestCategory", "Updated Test Description" );
        ServiceCategory mockCategory = new ServiceCategory("TestCategory", "Test Description");

        //Act
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));
        ServiceCategoryOutputDto outputDto = categoryService.updateCategory(1L, inputDto);

        //Assert
        assertNotNull(outputDto);
        assertEquals("UpdatedTestCategory", outputDto.name);
        assertEquals("Updated Test Description", outputDto.description);
    }

    @Test
    public void shouldDeleteCategory() {

        //Arrange
        ServiceCategory mockCategory = new ServiceCategory();

        //Act
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));

        //Assert
        assertDoesNotThrow(() -> categoryService.deleteCategory(1L));
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void whenDeleteCategory_CategoryNotFound() {

        //Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        //Act
        assertThrows(RecordNotFoundException.class, () -> categoryService.deleteCategory(1L));

        //Assert
        verify(categoryRepository, never()).deleteById(1L);
    }
}