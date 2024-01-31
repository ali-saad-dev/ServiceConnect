package nl.novi.serviceconnect.dtos;

import nl.novi.serviceconnect.models.RequestState;

public class ServiceRequestOutputDto {

    public Long id;

    public String username;

    public String message;
    public RequestState requestState;

    public ServiceOutputDto service;
}
