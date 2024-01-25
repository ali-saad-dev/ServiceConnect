package nl.novi.serviceconnect.dtos;

import nl.novi.serviceconnect.models.RequestState;
import nl.novi.serviceconnect.models.Service;

public class ServiceRequestOutputDto {
    public Long id;

    public String message;
    public RequestState requestState;

    public Service service;
}
