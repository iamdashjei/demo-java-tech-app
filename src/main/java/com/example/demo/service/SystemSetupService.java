package com.example.demo.service;

import com.example.demo.dto.SystemSetupDTO;
import com.example.demo.model.Consumer;
import com.example.demo.model.SystemSetup;
import com.example.demo.repository.SystemSetupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SystemSetupService {

    private final SystemSetupRepository systemSetupRepository;

    public SystemSetupService(SystemSetupRepository systemSetupRepository){
        this.systemSetupRepository = systemSetupRepository;
    }

    public Page<SystemSetupDTO> getSystemSetupPaginated(Pageable pageable, String searchTerm){
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            return systemSetupRepository.findByNameContainingIgnoreCase(searchTerm, pageable)
                    .map(SystemSetup::toDTO);
        } else {
            return systemSetupRepository.findAll(pageable)
                    .map(SystemSetup::toDTO);
        }
    }

    public List<SystemSetupDTO> getAllSystemSetup() {
        return systemSetupRepository.findAll().stream()
                .map(SystemSetup::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<SystemSetupDTO> getSystemSetupById(Long id) {
        return systemSetupRepository.findById(id)
                .map(SystemSetup::toDTO);
    }

    public SystemSetupDTO createSystemSetup(SystemSetupDTO systemSetupDTO) {
        SystemSetup systemSetup = systemSetupDTO.toEntity();
        systemSetup.setCreatedBy("Admin");
        systemSetup.setCreatedAt(LocalDateTime.now());
        systemSetup.setStatus(1);
        SystemSetup createdSystemSetup = systemSetupRepository.save(systemSetup);
        return createdSystemSetup.toDTO();
    }

    public Optional<SystemSetupDTO> updateSystemSetup(Long id, SystemSetupDTO systemSetupDTODetails) {
        return systemSetupRepository.findById(id)
                .map(item -> {
                   item.setName(systemSetupDTODetails.getName());
                   item.setBaseUrl(systemSetupDTODetails.getBaseUrl());
                   item.setAuthenticationMethod(systemSetupDTODetails.getAuthenticationMethod());
                   item.setKey(systemSetupDTODetails.getKey());
                   item.setValue(systemSetupDTODetails.getValue());
                   item.setAuthenticationPlace(systemSetupDTODetails.getAuthenticationPlace());
                   item.setLastModifiedAt(LocalDateTime.now());
                   item.setLastModifiedBy("Admin");
                   SystemSetup result = systemSetupRepository.save(item);
                   return result.toDTO();
                });
    }

    public SystemSetupDTO deleteSystemSetup(Long id) {
        Optional<SystemSetup> detail = systemSetupRepository.findById(id);
        return detail.map(setup -> {
            setup.setStatus(0);
            setup.setLastModifiedBy("Admin");
            setup.setLastModifiedAt(LocalDateTime.now());
            return systemSetupRepository.save(setup).toDTO();
        }).orElse(null);
    }
}
