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
    public double price;
    public ServiceState state;
}
