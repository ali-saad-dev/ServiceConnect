package nl.novi.serviceconnect.core.services;

import nl.novi.serviceconnect.core.dtos.ServiceCategoryInputDto;
import nl.novi.serviceconnect.core.dtos.ServiceCategoryOutputDto;
import nl.novi.serviceconnect.core.mappers.CategoryMapper;
import nl.novi.serviceconnect.core.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.core.helpers.Helpers;
import nl.novi.serviceconnect.infrastructure.models.ServiceCategory;
import nl.novi.serviceconnect.infrastructure.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements IServiceCategory {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ServiceCategoryOutputDto createServiceCategory(ServiceCategoryInputDto inputDto) {

        ServiceCategory serviceCategoryResult =  CategoryMapper.fromDtoToCategory(inputDto);
        categoryRepository.save(serviceCategoryResult);

        return CategoryMapper.fromCategoryToDto(serviceCategoryResult);
    }

    @Override
    public List<ServiceCategoryOutputDto> getAllCategories() {

        List<ServiceCategory> CategoriesList = categoryRepository.findAll();
        List<ServiceCategoryOutputDto> outputDto = new ArrayList<>();

        if (CategoriesList.isEmpty()) throw new RecordNotFoundException("No categories found");

        CategoriesList.sort(Comparator.comparing(ServiceCategory::getId));

        for(ServiceCategory category : CategoriesList) {
            outputDto.add(CategoryMapper.fromCategoryToDto(category));
        }

        return outputDto;
    }

    @Override
    public ServiceCategoryOutputDto getCategoryById(Long id) {
        Optional<ServiceCategory> optionalServiceCategory = categoryRepository.findById(id);

        ServiceCategory result = optionalServiceCategory.orElseThrow(() ->
                new RecordNotFoundException("No serviceCategory found with id: " + id));

        return CategoryMapper.fromCategoryToDto(result);
    }

    @Override
    public ServiceCategoryOutputDto updateCategory(Long id, ServiceCategoryInputDto inputDto) {
        ServiceCategory existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category with id: " + id + " not found"));
        updateCategoryFields(existingCategory, inputDto);
        categoryRepository.save(existingCategory);

        return CategoryMapper.fromCategoryToDto(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<ServiceCategory> optionalServiceCategory = categoryRepository.findById(id);

        if (optionalServiceCategory.isEmpty()) {
            throw new RecordNotFoundException("Service with id " + id + " not found");
        } else {
            categoryRepository.deleteById(id);
        }
    }

    private void updateCategoryFields(ServiceCategory category, ServiceCategoryInputDto inputDto) {
        if (Helpers.isNotNullOrEmpty(inputDto.getName())) {
            category.setName(inputDto.getName());
        }
        if (Helpers.isNotNullOrEmpty(inputDto.getDescription())) {
            category.setDescription(inputDto.getDescription());
        }
    }
}
