package nl.novi.serviceconnect.core.services;


import nl.novi.serviceconnect.core.dtos.TransactionInputDto;
import nl.novi.serviceconnect.core.dtos.TransactionOutputDto;
import nl.novi.serviceconnect.core.dtos.TransactionUpdateDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITransactionService {

    TransactionOutputDto createTransaction(TransactionInputDto transactionInputDto);

    List<TransactionOutputDto> getAllTransaction();

    ResponseEntity<byte[]> getTransactionById(Long id);

    TransactionOutputDto updateTransaction(Long id, TransactionUpdateDto inputDto);

    void deleteTransaction(Long id);
}
