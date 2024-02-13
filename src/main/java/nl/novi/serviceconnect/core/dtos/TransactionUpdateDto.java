package nl.novi.serviceconnect.core.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TransactionUpdateDto {

    private Date transactionDate;

    @JsonProperty("isPayed")
    private boolean isPayed;

    public void setTransactionDate(Date transactionDate) { this.transactionDate = transactionDate; }
    public void setPayed(boolean payed) { isPayed = payed; }
    public Date getTransactionDate() { return transactionDate; }
    public boolean getPayed() {
        return isPayed;
    }
}
