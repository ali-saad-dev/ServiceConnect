package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.core.dtos.TransactionInputDto;
import nl.novi.serviceconnect.core.dtos.TransactionUpdateDto;
import nl.novi.serviceconnect.core.dtos.TransactionOutputDto;
import nl.novi.serviceconnect.core.exceptions.FileAlreadyUploadedException;
import nl.novi.serviceconnect.core.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.infrastructure.models.ServiceRequest;
import nl.novi.serviceconnect.infrastructure.models.Transaction;
import nl.novi.serviceconnect.core.services.TransactionService;
import nl.novi.serviceconnect.infrastructure.repository.ServiceRequestRepository;
import nl.novi.serviceconnect.infrastructure.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    ServiceRequestRepository serviceRequestRepository;

    @InjectMocks
    TransactionService transactionService;

    @Test
    public void shouldGetServiceRequest() {
        //Arrange
        ServiceRequest mockServiceRequest = new ServiceRequest();
        mockServiceRequest.setId(1L);
        when(serviceRequestRepository.findById(1L)).thenReturn(Optional.of(mockServiceRequest));

        //Act
        ServiceRequest result = transactionService.getServiceRequest(1L);

        //Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void shouldGetFilePath_Success() {
        //Arrange
        String fileSeparator = File.separator;
        ServiceRequest serviceRequest = mock(ServiceRequest.class);
        when(serviceRequest.getId()).thenReturn(1L);
        String expectedFilePath = "invoicesPdf" + fileSeparator + "invoice-" + serviceRequest.getId()+ ".pdf";


        //Act
        String filePath = transactionService.getFilePath(serviceRequest);


        //Assert
        assertEquals(expectedFilePath, filePath);
        assertTrue(filePath.startsWith(expectedFilePath), "File path should start with: " + expectedFilePath);
    }

    @Test
    public void shouldThrowException_NoServiceRequestId() {
        //Arrange
        when(serviceRequestRepository.findById(2L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(RecordNotFoundException.class, () -> transactionService.getServiceRequest(2L));
    }

    @Test
    public void shouldGetAllTransaction() {
        //Arrange
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(mockTransaction));

        //Act
        List<TransactionOutputDto> outputDtoList = transactionService.getAllTransaction();

        //Assert
        assertEquals(1, outputDtoList.size());
        assertEquals(1L, outputDtoList.get(0).id);
    }

    @Test
    public void testGetAllTransaction_NoTransactionsFound() {
        //Arrange
        when(transactionRepository.findAll()).thenReturn(new ArrayList<>());

        //Act & Assert
        assertThrows(RecordNotFoundException.class, () -> transactionService.getAllTransaction());
    }

    @Test
    public void shouldGetTransactionById() {
        //Arrange
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        mockTransaction.setInvoice("invoice.pdf");
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(mockTransaction));

        //Act
        ResponseEntity<byte[]> responseEntity = transactionService.getTransactionById(1L);

        //Assert
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getHeaders().getContentDisposition().getFilename(), mockTransaction.getInvoice());
    }

    @Test
    public void testCreateTransaction_InvalidServiceRequestId() {
        //Arrange
        TransactionInputDto inputDto = new TransactionInputDto(new Date(2024-5-16),true,1L);

        //Act & Assert
        assertThrows(RecordNotFoundException.class, () -> transactionService.createTransaction(inputDto));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    public void shouldSaveTransaction() {
        //Arrange
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);

        //Act
        transactionService.saveTransaction(mockTransaction);

        //Assert
        verify(transactionRepository, times(1)).save(mockTransaction);
    }

    @Test
    public void whenNoTransactionFoundShouldReturn_TransactionNotFound() {
        //Arrange
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(RecordNotFoundException.class, () -> transactionService.getTransactionById(1L));
    }

    @Test
    public void shouldUpdateTransaction() {
        //Arrange
        TransactionUpdateDto inputDto = new TransactionUpdateDto();
        inputDto.setPayed(true);
        inputDto.setTransactionDate(new Date());

        Transaction existingTransaction = new Transaction();
        existingTransaction.setId(1L);
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(existingTransaction));

        //Act
        TransactionOutputDto outputDto = transactionService.updateTransaction(1L, inputDto);

        //Assert
        assertNotNull(outputDto);
        assertTrue(outputDto.isPayed);
        assertNotNull(outputDto.transactionDate);
    }

    @Test
    public void shouldValidateServiceRequestId_ServiceRequestIdExists() {
        //Arrange
        when(transactionRepository.existsById(1L)).thenReturn(true);
        TransactionInputDto inputDto = new TransactionInputDto(new Date(2024-5-16),true,1L);

        //Act & Assert
        assertThrows(FileAlreadyUploadedException.class, () -> transactionService.validateServiceRequestId(inputDto));
    }

    @Test
    public void shouldValidateServiceRequestId_ServiceRequestIdDoesNotExist() {
        //Arrange
        TransactionInputDto inputDto = new TransactionInputDto(new Date(2024-5-16),false,1L);
        when(transactionRepository.existsById(1L)).thenReturn(false);

        //Act & Assert
        assertDoesNotThrow(() -> transactionService.validateServiceRequestId(inputDto));
    }

    @Test
    public void shouldDeleteTransaction() {
        //Arrange
        Transaction mockTransaction = new Transaction();
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(mockTransaction));

        //Act & Assert
        assertDoesNotThrow(() -> transactionService.deleteTransaction(1L));
    }

    @Test
    public void whenDeleteTransaction_TransactionNotFound() {
        //Arrange
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        verify(transactionRepository, never()).deleteById(1L);
        assertThrows(RecordNotFoundException.class, () -> transactionService.deleteTransaction(1L));
    }
}