package nl.novi.serviceconnect.core.dtos;


import java.util.Date;

public class TransactionOutputDto {
    public Long id;
    public Date transactionDate;
    public boolean isPayed;
    public String  invoice;
    public Long serviceRequestId;
}
