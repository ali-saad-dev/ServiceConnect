package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.ServiceCategoryInputDto;
import nl.novi.serviceconnect.dtos.ServiceCategoryOutputDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.models.ServiceCategory;
import nl.novi.serviceconnect.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
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
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    @DisplayName("When calling createServiceCategory and data is correct, it should create a serviceCategory")
    @Test
    public void shouldCreateServiceCategory() {
        //Arrange
        ServiceCategoryInputDto inputDto = new ServiceCategoryInputDto("TestCategory", "Test Description" );

        //Act
        ServiceCategoryOutputDto outputDto = categoryService.createServiceCategory(inputDto);

        //Assert
        assertNotNull(outputDto);
        assertEquals(outputDto.name, inputDto.getName());
        assertEquals(outputDto.description, inputDto.getDescription());
    }

    @Test
    public void shouldGetAllCategories() {
        //Arrange
        ServiceCategory mockCategory = new ServiceCategory("TestCategory", "Test Description");
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(mockCategory));

        //Act
        List<ServiceCategoryOutputDto> outputDtoList = categoryService.getAllCategories();

        //Assert
        assertEquals(1, outputDtoList.size());
        assertEquals(outputDtoList.get(0).name, mockCategory.getName());
        assertEquals(outputDtoList.get(0).description, mockCategory.getDescription());
    }

    @Test
    public void whenNoCategories_ReturnNoCategoriesFound() {
        //Arrange
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        //Act & Assert
        assertThrows(RecordNotFoundException.class, () -> categoryService.getAllCategories());
    }

    @Test
    public void shouldGetCategoryById() {
        //Arrange
        ServiceCategory mockCategory = new ServiceCategory("TestCategory", "Test Description");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));

        //Act
        ServiceCategoryOutputDto outputDto = categoryService.getCategoryById(1L);

        //Assert
        assertNotNull(outputDto);
        assertEquals(outputDto.name, mockCategory.getName());
        assertEquals(outputDto.description, mockCategory.getDescription());
    }

    @Test
    public void whenNoCategoryFoundShouldReturn_CategoryNotFound() {
        //Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(RecordNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    public void shouldUpdateCategory() {
        //Arrange
        ServiceCategoryInputDto inputDto = new ServiceCategoryInputDto("UpdatedTestCategory", "Updated Test Description" );
        ServiceCategory mockCategory = new ServiceCategory("TestCategory", "Test Description");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));

        //Act
        ServiceCategoryOutputDto outputDto = categoryService.updateCategory(1L, inputDto);

        //Assert
        assertNotNull(outputDto);
        assertEquals(outputDto.name, inputDto.getName());
        assertEquals(outputDto.description, inputDto.getDescription());
    }

    @Test
    public void shouldDeleteCategory() {
        //Arrange
        ServiceCategory mockCategory = new ServiceCategory();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));

        //Act & Assert
        assertDoesNotThrow(() -> categoryService.deleteCategory(1L));
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void whenDeleteCategory_CategoryNotFound() {
        //Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(RecordNotFoundException.class, () -> categoryService.deleteCategory(1L));
        verify(categoryRepository, never()).deleteById(1L);
    }
}