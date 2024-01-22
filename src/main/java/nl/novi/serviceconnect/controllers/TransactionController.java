package nl.novi.serviceconnect.controllers;

import jakarta.validation.Valid;
import nl.novi.serviceconnect.dtos.TransactionInputDto;
import nl.novi.serviceconnect.dtos.TransactionOutputDto;
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

//    @GetMapping("/{id}")
//    public ResponseEntity<TransactionOutputDto> getServiceById(@PathVariable Long id) {
//        TransactionOutputDto transactionId = TransactionService.getTransactionById(id);
//        return ResponseEntity.ok(transactionId);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ServiceOutputDto> updateService(@PathVariable Long id, @RequestBody ServiceInputDto serviceInputDto) {
//        try {
//            ServiceOutputDto updatedService = service.updateService(id, serviceInputDto);
//            return ResponseEntity.ok(updatedService);
//        } catch (RecordNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ServiceInputDto> deleteService(@PathVariable Long id) {
//        try {
//            service.deleteService(id);
//            return ResponseEntity.ok().build();
//        } catch (RecordNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

}

