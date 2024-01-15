package nl.novi.serviceconnect.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import nl.novi.serviceconnect.models.ServiceState;

public class ServiceRequestInputDto {
    public Long id;
    @NotBlank(message = "message is mandatory")
    @Size(min=3, max=256)
    public String message;
    public ServiceState state;

    public String getMessage() {
        return message;
    }

    public ServiceState getState() {
        return state;
    }
}
