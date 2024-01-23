package nl.novi.serviceconnect.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TransactionUpdateDto {
    private Date transactionDate;
    @JsonProperty("isPayed")
    private boolean isPayed;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public boolean getPayed() {
        return isPayed;
    }
}
