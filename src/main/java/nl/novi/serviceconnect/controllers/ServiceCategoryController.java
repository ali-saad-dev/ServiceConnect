package nl.novi.serviceconnect.controllers;

import jakarta.validation.Valid;
import nl.novi.serviceconnect.dtos.ServiceCategoryInputDto;
import nl.novi.serviceconnect.dtos.ServiceCategoryOutputDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.services.IServiceCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static nl.novi.serviceconnect.helpper.StringHelpers.getObjectResponseEntity;

@RestController
@RequestMapping("/serviceCategory")
public class ServiceCategoryController {

    private final IServiceCategory serviceCategory;

    public ServiceCategoryController(IServiceCategory serviceCategory) { this.serviceCategory = serviceCategory; }

    @PostMapping
    public ResponseEntity<Object> createCategory(@Valid @RequestBody ServiceCategoryInputDto inputDto, BindingResult br) {

        if (br.hasFieldErrors()) {
            return getObjectResponseEntity(br);
        }
        else {
            ServiceCategoryOutputDto category = serviceCategory.createServiceCategory(inputDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + category.id).toUriString());

            return ResponseEntity.created(uri).body(category);
        }
    }

    @GetMapping
    public ResponseEntity<List<ServiceCategoryOutputDto>> getAllCategories() {
        return ResponseEntity.ok(serviceCategory.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryOutputDto> getCategoryById(@PathVariable Long id) {
        ServiceCategoryOutputDto serviceCategoryId = serviceCategory.getCategoryById(id);
        return ResponseEntity.ok(serviceCategoryId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCategoryOutputDto> updateCategory(@PathVariable Long id, @RequestBody ServiceCategoryInputDto inputDto) {
        try {
            ServiceCategoryOutputDto updatedServiceCategory = serviceCategory.updateCategory(id, inputDto);
            return ResponseEntity.ok(updatedServiceCategory);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceCategoryInputDto> deleteCategory(@PathVariable Long id) {
        try {
            serviceCategory.deleteCategory(id);
            return ResponseEntity.ok().build();
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
