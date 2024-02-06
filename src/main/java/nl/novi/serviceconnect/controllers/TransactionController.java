package nl.novi.serviceconnect.controllers;

import jakarta.validation.Valid;
import nl.novi.serviceconnect.dtos.TransactionInputDto;
import nl.novi.serviceconnect.dtos.TransactionOutputDto;
import nl.novi.serviceconnect.dtos.TransactionUpdateDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.services.ITransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static nl.novi.serviceconnect.helpper.StringHelpers.getObjectResponseEntity;


@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final ITransactionService TransactionService;
    public TransactionController(ITransactionService transactionService) {
        TransactionService = transactionService;
    }
    @PostMapping
    public ResponseEntity<Object> createTransaction(@Valid @RequestBody TransactionInputDto inputDto, BindingResult br) {
        if (br.hasFieldErrors()) return getObjectResponseEntity(br);

        TransactionOutputDto outputDto = TransactionService.createTransaction(inputDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + outputDto.id).toUriString());
        return ResponseEntity.created(uri).body(outputDto);
    }

    @GetMapping
    public ResponseEntity<List<TransactionOutputDto>> getAllTransaction() {
        return ResponseEntity.ok(TransactionService.getAllTransaction());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getTransactionById(@PathVariable Long id) {
        try {
            ResponseEntity<byte[]> responseEntity = TransactionService.getTransactionById(id);
            return responseEntity;
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionOutputDto> updateTransaction(@PathVariable Long id, @RequestBody TransactionUpdateDto inputDto) {
        try {
            TransactionOutputDto updatedTransaction = TransactionService.updateTransaction(id, inputDto);
            return ResponseEntity.ok(updatedTransaction);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TransactionInputDto> deleteTransaction(@PathVariable Long id) {
        try {
            TransactionService.deleteTransaction(id);
            return ResponseEntity.ok().build();
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

