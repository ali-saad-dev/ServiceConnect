package nl.novi.serviceconnect.dtos;

import nl.novi.serviceconnect.models.Service;
import nl.novi.serviceconnect.models.ServiceState;

public class ServiceRequestOutputDto {
    public Long id;

    public String message;
    public ServiceState state;

    public Service service;
}
