package nl.novi.serviceconnect.core.services;

import nl.novi.serviceconnect.core.dtos.TransactionInputDto;
import nl.novi.serviceconnect.core.dtos.TransactionOutputDto;
import nl.novi.serviceconnect.core.dtos.TransactionUpdateDto;
import nl.novi.serviceconnect.core.exceptions.FileAlreadyUploadedException;
import nl.novi.serviceconnect.core.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.core.helpers.Helpers;
import nl.novi.serviceconnect.core.helpers.InvoiceGenerator;
import nl.novi.serviceconnect.core.mappers.TransactionMapper;
import nl.novi.serviceconnect.infrastructure.models.ServiceRequest;
import nl.novi.serviceconnect.infrastructure.models.Transaction;
import nl.novi.serviceconnect.infrastructure.repository.ServiceRequestRepository;
import nl.novi.serviceconnect.infrastructure.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;

    private final ServiceRequestRepository requestRepository;

    public TransactionService(TransactionRepository repository, ServiceRequestRepository requestRepository) {
        this.transactionRepository = repository;
        this.requestRepository = requestRepository;
    }

    public String getFilePath(ServiceRequest serviceRequest) {

            File targetDirectory = new File("invoicesPdf");

            if (!targetDirectory.exists()) {
                targetDirectory.mkdirs();
            }
            String fileName = "invoice-" + serviceRequest.getId() + ".pdf";

        return targetDirectory.getPath() + File.separator + fileName;
    }

    @Override
    public TransactionOutputDto createTransaction(TransactionInputDto transactionInputDto) {
        Transaction transaction = TransactionMapper.fromDtoToTransaction(transactionInputDto);

        validateServiceRequestId(transactionInputDto);

        ServiceRequest serviceRequest = getServiceRequest(transactionInputDto.getServiceRequestId());
        String filePath = getFilePath(serviceRequest);
        transaction.setInvoice(filePath);
        transaction.setServiceRequest(serviceRequest);

        InvoiceGenerator.generateInvoice(serviceRequest, transaction);

        saveTransaction(transaction);

        return TransactionMapper.fromTransactionToDto(transaction);
    }

    public void validateServiceRequestId(TransactionInputDto transactionInputDto) {
        if (transactionRepository.existsById(transactionInputDto.getServiceRequestId())) {
            throw new FileAlreadyUploadedException("ServiceRequestId already exists in the database ServiceRequestId: " + transactionInputDto.getServiceRequestId());
        }
    }

    public ServiceRequest getServiceRequest(Long serviceRequestId) {
        return requestRepository.findById(serviceRequestId)
                .orElseThrow(() -> new RecordNotFoundException("ServiceRequestId not found with id: " + serviceRequestId));
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionOutputDto> getAllTransaction() {
        List<Transaction> transactionList = transactionRepository.findAll();
        List<TransactionOutputDto> transactionDtoList = new ArrayList<>();

        if (transactionList.isEmpty()) {
            throw new RecordNotFoundException("No Transaction found");
        }

        transactionList.sort(Comparator.comparing(Transaction::getId));

        for(Transaction transaction : transactionList) {
            transactionDtoList.add(TransactionMapper.fromTransactionToDto(transaction));
        }
        return transactionDtoList;
    }

    @Override
    public ResponseEntity<byte[]> getTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);

        Transaction result = transaction.orElseThrow(() ->
                new RecordNotFoundException("No transaction found with id: " + id));

        String invoiceFilePath = result.getInvoice();

        byte[] pdfContent = readPdfFile(invoiceFilePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice.pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    private byte[] readPdfFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public TransactionOutputDto updateTransaction(Long id, TransactionUpdateDto inputDto) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Transaction with id: " + id + " not found"));
        Transaction result =  updateServiceFields(existingTransaction, inputDto);
        transactionRepository.save(result);

        return TransactionMapper.fromTransactionToDto(result);
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
