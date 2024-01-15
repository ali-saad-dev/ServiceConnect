package nl.novi.serviceconnect.dtos;

import nl.novi.serviceconnect.models.ServiceState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ServiceInputDto {
    public Long id;
    @NotBlank(message = "Name is mandatory")
    @Size(min=3, max=128)
    public String name;
    public String description;
    @Positive
    public Double price;
    public ServiceState state;

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
}
