package nl.novi.serviceconnect.models;

import jakarta.persistence.*;

@Entity
@Table(name="service_category")
public class ServiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="categoryName", length = 128)
    private String name;

    @Column(name = "description", length = 512)
    private String description;

//    @OneToMany(mappedBy = "Service")
//    private List<Service> services;
    public Long getId() {
        return id;
    }
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

//    public List<Service> getServices() {
//        return services;
//    }
//
//    public void setServices(List<Service> services) {
//        this.services = services;
//    }
}