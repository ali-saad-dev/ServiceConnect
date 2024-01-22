package nl.novi.serviceconnect.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.File;
import java.util.Date;


@Entity
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date transactionDate;
    private boolean isPayed;
    private File invoice;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "serviceRequest_id", referencedColumnName = "id")
    private ServiceRequest serviceRequest;
    public Transaction() {}
    public Transaction(Long id, Date transactionDate, boolean isPayed) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.isPayed = isPayed;
    }
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

    public File getInvoice() {
        return invoice;
    }

    public void setInvoice(File invoice) {
        this.invoice = invoice;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }
}
