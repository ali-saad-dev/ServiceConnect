package nl.novi.serviceconnect.models;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="serviceRequest")
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @Enumerated(EnumType.STRING)
    private RequestState requestState;
    @ManyToOne
    @JoinColumn(name="service_id")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    private Service service;

    @OneToOne(mappedBy = "serviceRequest")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name="user_name")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    private User user;

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public RequestState getState() { return requestState; }
    public void setState(RequestState state) { this.requestState = state; }
    public Service getService() { return service; }
    public void setService(Service service) { this.service = service; }
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
