package nl.novi.serviceconnect.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ServiceCategoryInputDto {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    @Size(min=3, max=128)
    private String name;
    @NotBlank(message = "description is mandatory")
    private String description;
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
