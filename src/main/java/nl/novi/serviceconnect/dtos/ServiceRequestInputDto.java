package nl.novi.serviceconnect.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import nl.novi.serviceconnect.models.Service;
import nl.novi.serviceconnect.models.ServiceState;

public class ServiceRequestInputDto {

    private Long id;
    @NotBlank(message = "message is mandatory")
    @Size(min=3, max=256)
    private String message;
    private ServiceState state;

    private Service service;
    public String getMessage() {
        return message;
    }

    public ServiceState getState() {
        return state;
    }

    public Service getService() {
        return service;
    }
}
