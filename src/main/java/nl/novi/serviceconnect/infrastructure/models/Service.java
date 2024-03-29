package nl.novi.serviceconnect.infrastructure.models;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;


@Entity
@Table(name="services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="service_name", length = 128)
    private String name;

    private String description;

    @Column(name="price", length = 128)
    private double price;

    @Enumerated(EnumType.STRING)
    private ServiceState state;

    @OneToMany(mappedBy = "service")
    private List<ServiceRequest> serviceRequests;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    private ServiceCategory category;

    @ManyToOne
    @JoinColumn(name = "user_name")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    private User user;

    @OneToOne(mappedBy = "service")
    private FileOrImageData fileOrImageData;

    public Service() {}

    public Service(Long id, String name, String description, double price, ServiceState state, ServiceCategory serviceCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.state = state;
        this.category = serviceCategory;
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
    public ServiceCategory getCategory() { return category; }
    public void setCategory(ServiceCategory category) { this.category = category; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public FileOrImageData getFileOrImageData() { return fileOrImageData; }
    public void setFileOrImageData(FileOrImageData fileOrImageData) { this.fileOrImageData = fileOrImageData; }

}
