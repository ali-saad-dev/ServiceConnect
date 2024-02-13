package nl.novi.serviceconnect.core.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import nl.novi.serviceconnect.infrastructure.models.RequestState;
import nl.novi.serviceconnect.infrastructure.models.Service;

public class ServiceRequestInputDto {
    @NotBlank(message = "message is mandatory")
    @Size(min=3, max=256)
    private String message;
    @JsonProperty("requestState")
    private RequestState requestState;
    private Service service;
    public String getMessage() {
        return message;
    }
    public RequestState getState() { return requestState; }
    public Service getService() {
        return service;
    }
}
