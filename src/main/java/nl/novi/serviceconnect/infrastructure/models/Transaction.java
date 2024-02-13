package nl.novi.serviceconnect.infrastructure.models;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;


@Entity
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date transactionDate;
    private boolean isPayed;
    private String invoice;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "serviceRequest_id")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    private ServiceRequest serviceRequest;
    public Transaction() {}
    public Transaction(Long id, Date transactionDate, boolean isPayed, String invoice, ServiceRequest serviceRequest) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.isPayed = isPayed;
        this.invoice = invoice;
        this.serviceRequest = serviceRequest;
    }

    public void setId(Long id) {this.id = id; }
    public Long getId() {
        return id;
    }
    public Date getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    public boolean getPayed() {
        return isPayed;
    }
    public void setPayed(boolean isPayed) {
        this.isPayed = isPayed;
    }
    public String getInvoice() {
        return invoice;
    }
    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }
    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }
}
