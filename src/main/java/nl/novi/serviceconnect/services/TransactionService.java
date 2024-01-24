package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.TransactionInputDto;
import nl.novi.serviceconnect.dtos.TransactionOutputDto;
import nl.novi.serviceconnect.dtos.TransactionUpdateDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.helpper.Helpers;
import nl.novi.serviceconnect.helpper.InvoiceGenerator;
import nl.novi.serviceconnect.models.ServiceRequest;
import nl.novi.serviceconnect.models.Transaction;
import nl.novi.serviceconnect.repository.ServiceRequestRepository;
import nl.novi.serviceconnect.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import nl.novi.serviceconnect.helpper.Mapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {
    private final TransactionRepository transactionRepository;

    private final ServiceRequestRepository requestRepository;
    public TransactionService(TransactionRepository repository, ServiceRequestRepository requestRepository) {
        this.transactionRepository = repository;
        this.requestRepository = requestRepository;
    }

    private String saveFile() {
        try {
            File targetDirectory = new File("invoicesPdf");

            if (!targetDirectory.exists()) {
                targetDirectory.mkdirs();
            }

            String fileName = "invoice_" + System.currentTimeMillis() + ".pdf";

            String filePath = targetDirectory.getAbsolutePath() + File.separator + fileName;

            return filePath;
        } catch (Exception e) {

            throw new RuntimeException("Error generating and saving PDF file", e);
        }
    }

    @Override
    public TransactionOutputDto createTransaction(TransactionInputDto transactionInputDto) {

        Transaction transaction = Mapper.fromDtoToTransaction(transactionInputDto);

        String filePath = saveFile();
        transaction.setInvoice(filePath);

        ServiceRequest serviceRequest = requestRepository.findById(transactionInputDto.getServiceRequestId())
                .orElseThrow(() -> new RecordNotFoundException("ServiceRequestId not found with id: " + transactionInputDto.getServiceRequestId()));
        transaction.setServiceRequest(serviceRequest);
        InvoiceGenerator.generateInvoice(serviceRequest);
       transactionRepository.save(transaction);

        return Mapper.fromTransactionToDto(transaction);
    }

    @Override
    public List<TransactionOutputDto> getAllTransaction() {
        List<Transaction> transactionList = transactionRepository.findAll();
        List<TransactionOutputDto> transactionDtoList = new ArrayList<>();

        if (transactionList.isEmpty()) {
            throw new RecordNotFoundException("No Transaction found");
        }

        for(Transaction transaction : transactionList) {
            transactionDtoList.add(Mapper.fromTransactionToDto(transaction));
        }
        return transactionDtoList;
    }

    @Override
    public TransactionOutputDto getTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);

        Transaction result = transaction.orElseThrow(() ->
                new RecordNotFoundException("No transaction found with id: " + id));

        return Mapper.fromTransactionToDto(result);
    }

    @Override
    public TransactionOutputDto updateTransaction(Long id, TransactionUpdateDto inputDto) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Transaction with id: " + id + " not found"));
        Transaction result =  updateServiceFields(existingTransaction, inputDto);
        transactionRepository.save(result);

        return Mapper.fromTransactionToDto(result);
    }

    private Transaction updateServiceFields(Transaction transaction, TransactionUpdateDto inputDto) {

        return new Transaction(
                transaction.getId(),
                Helpers.isNotNullOrEmptyDate(inputDto.getTransactionDate()) ? inputDto.getTransactionDate() : transaction.getTransactionDate(),
                inputDto.getPayed(),
                transaction.getInvoice(),
                transaction.getServiceRequest()
        );
    }
    @Override
    public void deleteTransaction(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new RecordNotFoundException("Transaction with id " + id + " not found");
        } else {
            transactionRepository.deleteById(id);
        }
    }
}
