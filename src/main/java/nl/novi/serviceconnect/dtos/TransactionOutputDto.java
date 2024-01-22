package nl.novi.serviceconnect.dtos;
import java.io.File;
import java.util.Date;

public class TransactionOutputDto {
    public Long id;
    public Date transactionDate;
    public boolean isPayed;
    public File invoice;
    public Long serviceRequestId;
}
