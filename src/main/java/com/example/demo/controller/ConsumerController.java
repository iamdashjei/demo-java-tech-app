package com.example.demo.controller;

import com.example.demo.dto.ConsumerDTO;
import com.example.demo.service.ConsumerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/consumer")
@CrossOrigin(origins = "http://localhost:4200")
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping
    public ResponseEntity<Page<ConsumerDTO>> getConsumersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ConsumerDTO> consumerPage = consumerService.getConsumersPaginated(pageable, search);
        return ResponseEntity.ok(consumerPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumerDTO> getConsumerById(@PathVariable Long id) {
        Optional<ConsumerDTO> consumerDTO = consumerService.getConsumerById(id);
        return consumerDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ConsumerDTO> createConsumer(@RequestBody ConsumerDTO consumerDTO) {
        ConsumerDTO createdData = consumerService.createConsumer(consumerDTO);
        return new ResponseEntity<>(createdData, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumerDTO> updateConsumer(@PathVariable Long id, @RequestBody ConsumerDTO consumerDTO) {
        Optional<ConsumerDTO> consumerData = consumerService.updateConsumer(id, consumerDTO);
        return consumerData.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumer(@PathVariable Long id){
        ConsumerDTO deleteConsumer = consumerService.deleteConsumer(id);
        return deleteConsumer != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
