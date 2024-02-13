package nl.novi.serviceconnect.core.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.novi.serviceconnect.infrastructure.models.ServiceCategory;
import nl.novi.serviceconnect.infrastructure.models.ServiceState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public class ServiceInputDto {
    @NotBlank(message = "Name is mandatory")
    @Size(min=3, max=128)
    private String name;
    @NotBlank(message = "description is mandatory")
    @Size(min=3, max=128)
    private String description;
    @Positive
    private double price;
    private ServiceState state;
    @JsonProperty("serviceCategory")
    private ServiceCategory serviceCategory;
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Double getPrice() { return price; }
    public ServiceState getState() { return state; }
    public ServiceCategory getCategory() { return serviceCategory; }
}
