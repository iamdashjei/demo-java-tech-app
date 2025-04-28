package com.example.demo.controller;

import com.example.demo.dto.SystemSetupDTO;
import com.example.demo.service.SystemSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/system-setups")
@CrossOrigin(origins = "http://localhost:4200")
public class SystemSetupController {

    private final SystemSetupService systemSetupService;

    public SystemSetupController(SystemSetupService systemSetupService) {
        this.systemSetupService = systemSetupService;
    }

    @GetMapping
    public ResponseEntity<List<SystemSetupDTO>> getAllSystemSetup(){
        return new ResponseEntity<>(systemSetupService.getAllSystemSetup(), HttpStatus.OK);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<SystemSetupDTO>> getSystemSetupPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SystemSetupDTO> setupPage = systemSetupService.getSystemSetupPaginated(pageable, search);
        return ResponseEntity.ok(setupPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemSetupDTO> getSystemSetupById(@PathVariable Long id) {
        Optional<SystemSetupDTO> systemSetupDTO = systemSetupService.getSystemSetupById(id);
        return systemSetupDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<SystemSetupDTO> createSystemSetup(@RequestBody SystemSetupDTO systemSetupDTO){
        SystemSetupDTO setupDTO = systemSetupService.createSystemSetup(systemSetupDTO);
        return new ResponseEntity<>(setupDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemSetupDTO> updateSystemSetup(@PathVariable Long id, @RequestBody SystemSetupDTO systemSetupDTO) {
        Optional<SystemSetupDTO> updatedSystemSetup = systemSetupService.updateSystemSetup(id, systemSetupDTO);
        return updatedSystemSetup.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemSetupDTO> deleteSystemSetup(@PathVariable Long id) {
        SystemSetupDTO deleteSystemSetup = systemSetupService.deleteSystemSetup(id);
        return deleteSystemSetup != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
