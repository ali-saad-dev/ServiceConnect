package nl.novi.serviceconnect.dtos;

import nl.novi.serviceconnect.models.ServiceCategory;
import nl.novi.serviceconnect.models.ServiceState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ServiceInputDto {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    @Size(min=3, max=128)
    private String name;
    @NotBlank(message = "description is mandatory")
    @Size(min=3, max=128)
    private String description;
    @Positive
    private double price;
    private ServiceState state;
    private ServiceCategory serviceCategory;
    public Long getId() { return id; }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public ServiceState getState() {
        return state;
    }

    public ServiceCategory getCategory() { return serviceCategory; }
}
