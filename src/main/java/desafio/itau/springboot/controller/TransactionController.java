package desafio.itau.springboot.controller;


import desafio.itau.springboot.dto.TransactionRequest;
import desafio.itau.springboot.model.Transaction;
import desafio.itau.springboot.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/transacao")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction (@Valid @RequestBody TransactionRequest request){
        if (request.getDataHora().isAfter(OffsetDateTime.now()) || request.getValor() <= 0) {
            return ResponseEntity.unprocessableEntity().build();
        }
        service.addTransaction(new Transaction(request.getValor(), request.getDataHora()));
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping
    public ResponseEntity<Void> clearTransactions(){
        service.clearTransactions();
        return ResponseEntity.ok().build();
    }



}
