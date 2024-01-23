package nl.novi.serviceconnect.services;


import nl.novi.serviceconnect.dtos.TransactionInputDto;
import nl.novi.serviceconnect.dtos.TransactionOutputDto;
import nl.novi.serviceconnect.dtos.TransactionUpdateDto;

import java.util.List;

public interface ITransactionService {

    TransactionOutputDto createTransaction(TransactionInputDto transactionInputDto);

    List<TransactionOutputDto> getAllTransaction();

    TransactionOutputDto getTransactionById(Long id);

    TransactionOutputDto updateTransaction(Long id, TransactionUpdateDto inputDto);

    void deleteTransaction(Long id);
}
