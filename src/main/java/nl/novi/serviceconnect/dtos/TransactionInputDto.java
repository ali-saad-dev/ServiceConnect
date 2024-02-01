package nl.novi.serviceconnect.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TransactionInputDto {
    private Date transactionDate;
    @JsonProperty("isPayed")
    private boolean isPayed;
    private Long serviceRequestId;
    public Date getTransactionDate() {
        return transactionDate;
    }
    public boolean getPayed() {
        return isPayed;
    }
    public Long getServiceRequestId() {
        return serviceRequestId;
    }
}
