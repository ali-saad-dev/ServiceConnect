package nl.novi.serviceconnect.core.mappers;

import nl.novi.serviceconnect.core.dtos.TransactionInputDto;
import nl.novi.serviceconnect.core.dtos.TransactionOutputDto;
import nl.novi.serviceconnect.infrastructure.models.Transaction;

public class TransactionMapper {

    public static TransactionOutputDto fromTransactionToDto(Transaction transaction){
        TransactionOutputDto dto = new TransactionOutputDto();
        dto.id = transaction.getId();
        dto.transactionDate = transaction.getTransactionDate();
        dto.isPayed = transaction.getPayed();
        dto.invoice = transaction.getInvoice();
        dto.serviceRequestId = transaction.getServiceRequest() != null ? transaction.getServiceRequest().getId() : null;
        return dto;
    }

    public static Transaction fromDtoToTransaction(TransactionInputDto dto){
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setPayed(dto.getPayed());
        return  transaction;
    }
}
