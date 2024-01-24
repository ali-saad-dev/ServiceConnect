package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.*;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.helpper.Helpers;
import nl.novi.serviceconnect.helpper.Mapper;
import nl.novi.serviceconnect.models.ServiceCategory;
import nl.novi.serviceconnect.repository.ServiceCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements IServiceCategory {

    private final ServiceCategoryRepository categoryRepository;

    public CategoryService(ServiceCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ServiceCategoryOutputDto createServiceCategory(ServiceCategoryInputDto inputDto) {
        ServiceCategory serviceCategoryResult =  categoryRepository.save(Mapper.fromDtoToCategory(inputDto));
        return Mapper.fromCategoryToDto(serviceCategoryResult);
    }

    @Override
    public List<ServiceCategoryOutputDto> getAllCategories() {
        List<ServiceCategory> CategoriesList = categoryRepository.findAll();
        List<ServiceCategoryOutputDto> outputDto = new ArrayList<>();

        if (CategoriesList.isEmpty()) {
            throw new RecordNotFoundException("No categories found");
        }

        for(ServiceCategory category : CategoriesList) {
            outputDto.add(Mapper.fromCategoryToDto(category));
        }

        return outputDto;
    }

    @Override
    public ServiceCategoryOutputDto getCategoryById(Long id) {
        Optional<ServiceCategory> optionalServiceCategory = categoryRepository.findById(id);

        ServiceCategory result = optionalServiceCategory.orElseThrow(() ->
                new RecordNotFoundException("No serviceCategory found with id: " + id));

        return Mapper.fromCategoryToDto(result);
    }

    @Override
    public ServiceCategoryOutputDto updateCategory(Long id, ServiceCategoryInputDto inputDto) {
        ServiceCategory existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category with id: " + id + " not found"));
        updateCategoryFields(existingCategory, inputDto);
        categoryRepository.save(existingCategory);

        return Mapper.fromCategoryToDto(existingCategory);
    }

    private void updateCategoryFields(ServiceCategory category, ServiceCategoryInputDto inputDto) {
        if (Helpers.isNotNullOrEmpty(inputDto.getName())) {
            category.setName(inputDto.getName());
        }
        if (Helpers.isNotNullOrEmpty(inputDto.getDescription())) {
            category.setDescription(inputDto.getDescription());
        }
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
}
