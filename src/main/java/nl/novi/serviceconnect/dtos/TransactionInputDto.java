package nl.novi.serviceconnect.dtos;

import java.io.File;
import java.util.Date;

public class TransactionInputDto {
    private Date transactionDate;
    private boolean isPayed;
    private Long serviceRequestId;

    public File invoice;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public boolean getPayed() {
        return isPayed;
    }
    public Long getServiceRequestId() {
        return serviceRequestId;
    }

    public File getInvoice() {
        return invoice;
    }
}
