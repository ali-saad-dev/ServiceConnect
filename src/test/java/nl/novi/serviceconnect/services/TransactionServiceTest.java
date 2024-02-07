package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.TransactionInputDto;
import nl.novi.serviceconnect.dtos.TransactionUpdateDto;
import nl.novi.serviceconnect.dtos.TransactionOutputDto;
import nl.novi.serviceconnect.exceptions.BadRequestException;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.models.ServiceRequest;
import nl.novi.serviceconnect.models.Transaction;
import nl.novi.serviceconnect.repository.ServiceRequestRepository;
import nl.novi.serviceconnect.repository.TransactionRepository;
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
class TransactionServiceTest {

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

        //Act
        when(serviceRequestRepository.findById(1L)).thenReturn(Optional.of(mockServiceRequest));
        ServiceRequest result = transactionService.getServiceRequest(1L);

        //Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void shouldSaveFile_Success() {

        //Arrange
        String fileSeparator = File.separator;

        //Act
        String filePath = transactionService.saveFile();
        String expectedFilePathPrefix = "invoicesPdf" + fileSeparator + "invoice_";

        //Assert
        assertTrue(filePath.startsWith(expectedFilePathPrefix), "File path should start with: " + expectedFilePathPrefix);
    }

    @Test
    public void shouldGetServiceRequestNonExistingId() {

        //Act
        when(serviceRequestRepository.findById(2L)).thenReturn(Optional.empty());

        //Assert
        assertThrows(RecordNotFoundException.class, () -> transactionService.getServiceRequest(2L));
    }

    @Test
    public void shouldGetAllTransaction() {

        //Arrange
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);

        //Act
        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(mockTransaction));
        List<TransactionOutputDto> outputDtoList = transactionService.getAllTransaction();

        //Assert
        assertEquals(1, outputDtoList.size());
        assertEquals(1L, outputDtoList.get(0).id);
    }

    @Test
    public void testGetAllTransaction_NoTransactionsFound() {

        //Act
        when(transactionRepository.findAll()).thenReturn(new ArrayList<>());

        //Assert
        assertThrows(RecordNotFoundException.class, () -> transactionService.getAllTransaction());
    }

    @Test
    public void shouldGetTransactionById() {

        //Arrange
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        mockTransaction.setInvoice("invoice.pdf");

        //Act
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(mockTransaction));
        ResponseEntity<byte[]> responseEntity = transactionService.getTransactionById(1L);

        //Assert
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals("invoice.pdf", responseEntity.getHeaders().getContentDisposition().getFilename());
    }

    @Test
    public void testCreateTransaction_InvalidServiceRequestId() {

        //Act
        TransactionInputDto inputDto = new TransactionInputDto(new Date(2024-5-16),true,1L);

        //Assert
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

        //Act
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        //Assert
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

        //Act
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(existingTransaction));
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

        //Act
        TransactionInputDto inputDto = new TransactionInputDto(new Date(2024-5-16),true,1L);


        //Assert
        assertThrows(BadRequestException.class, () -> transactionService.validateServiceRequestId(inputDto));
    }

    @Test
    public void shouldValidateServiceRequestId_ServiceRequestIdDoesNotExist() {

        //Arrange
        when(transactionRepository.existsById(1L)).thenReturn(false);

        //Act
        TransactionInputDto inputDto = new TransactionInputDto(new Date(2024-5-16),false,1L);

        //Assert
        assertDoesNotThrow(() -> transactionService.validateServiceRequestId(inputDto));
    }

    @Test
    public void shouldDeleteTransaction() {

        //Arrange
        Transaction mockTransaction = new Transaction();

        //Act
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(mockTransaction));

        //Assert
        assertDoesNotThrow(() -> transactionService.deleteTransaction(1L));
    }

    @Test
    public void whenDeleteTransaction_TransactionNotFound() {

        //Arrange
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        //Act
        assertThrows(RecordNotFoundException.class, () -> transactionService.deleteTransaction(1L));

        //Assert
        verify(transactionRepository, never()).deleteById(1L);
    }
}