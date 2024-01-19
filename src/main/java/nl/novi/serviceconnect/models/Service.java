package nl.novi.serviceconnect.models;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name="services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="services-name", length = 128)
    private String name;
    private String description;
    @Column(name="price", length = 128)
    private double price;
    @Enumerated(EnumType.STRING)
    private ServiceState state;

    @OneToMany(mappedBy = "service")
    private List<ServiceRequest> serviceRequests;
    public Service() {}
    public Service(Long id, String name, String description, double price, ServiceState state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.state = state;
    }
    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ServiceState getState() {
        return state;
    }

    public void setState(ServiceState state) {
        this.state = state;
    }
}
