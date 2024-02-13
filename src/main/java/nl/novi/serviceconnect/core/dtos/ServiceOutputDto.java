package nl.novi.serviceconnect.core.dtos;

import nl.novi.serviceconnect.infrastructure.models.ServiceCategory;
import nl.novi.serviceconnect.infrastructure.models.ServiceState;

public class ServiceOutputDto {

    public Long id;
    public String username;
    public String name;
    public String description;
    public double price;
    public ServiceState state;
    public ServiceCategory serviceCategory;
}
