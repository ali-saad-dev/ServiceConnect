package nl.novi.serviceconnect.core.dtos;

import nl.novi.serviceconnect.infrastructure.models.RequestState;

public class ServiceRequestOutputDto {

    public Long id;

    public String username;

    public String message;
    public RequestState requestState;

    public ServiceOutputDto service;
}
