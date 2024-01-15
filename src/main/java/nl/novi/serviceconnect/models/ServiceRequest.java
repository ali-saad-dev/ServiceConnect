package nl.novi.serviceconnect.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="serviceRequest")
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @Enumerated(EnumType.STRING)
    private ServiceState state;
    @ManyToOne
    @JoinColumn(name="service_id")
    private Service service;

    public Long getId() { return id; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public ServiceState getState() { return state; }

    public void setState(ServiceState state) { this.state = state; }

    public Service getService() { return service; }

    public void setService(Service service) { this.service = service; }
}
