package nl.novi.serviceconnect.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ServiceCategoryInputDto {
    @NotBlank(message = "Name is mandatory")
    @Size(min=3, max=128)
    private String name;
    @NotBlank(message = "description is mandatory")
    private String description;
    public String getName() { return name; }
    public String getDescription() { return description; }

    public ServiceCategoryInputDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
