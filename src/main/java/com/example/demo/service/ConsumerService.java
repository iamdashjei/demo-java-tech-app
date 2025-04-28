package com.example.demo.service;

import com.example.demo.dto.ConsumerDTO;
import com.example.demo.dto.FundDTO;
import com.example.demo.model.Consumer;
import com.example.demo.model.Fund;
import com.example.demo.model.FundAlias;
import com.example.demo.model.SystemSetup;
import com.example.demo.repository.ConsumerRepository;
import com.example.demo.repository.FundRepository;
import com.example.demo.repository.SystemSetupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final SystemSetupRepository systemSetupRepository;
    private final FundRepository fundRepository;

    public ConsumerService(ConsumerRepository consumerRepository, SystemSetupRepository systemSetupRepository, FundRepository fundRepository) {
        this.consumerRepository = consumerRepository;
        this.systemSetupRepository = systemSetupRepository;
        this.fundRepository = fundRepository;
    }

    public Page<ConsumerDTO> getConsumersPaginated(Pageable pageable, String searchTerm) {
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            return consumerRepository.findByNameContainingIgnoreCase(searchTerm, pageable)
                    .map(Consumer::toDTO);
        } else {
            return consumerRepository.findAll(pageable)
                    .map(Consumer::toDTO);
        }
    }

    public List<ConsumerDTO> getAllConsumers(){
        return consumerRepository.findAll().stream()
                .map(Consumer::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ConsumerDTO> getConsumerById(Long id){
        return consumerRepository.findById(id)
                .map(Consumer::toDTO);
    }

    @Transactional
    public ConsumerDTO createConsumer(ConsumerDTO consumerDTO){
        Consumer consumer = consumerDTO.toEntity();
        if (consumerDTO.getFunds() != null) {
            List<Fund> funds = consumerDTO.getFunds().stream()
                    .map(fundDTO -> {
                        Fund fund = new Fund();
                        fund.setName(fundDTO.getName());

                        if (fundDTO.getAliases() != null) {
                            List<FundAlias> fundAliases = fundDTO.getAliases().stream()
                                    .map(FundAlias::new)
                                    .toList();
                            fundAliases.forEach(alias -> alias.setFund(fund));
                            fund.setAliases(fundAliases);
                        }
                        return fund;
                    })
                    .toList();
            consumer.setFunds(funds);
        }

        if (consumerDTO.getSystemSetupId() != null){
            Optional<SystemSetup> systemSetupId = systemSetupRepository.findById(consumerDTO.getSystemSetupId());
            systemSetupId.ifPresent(consumer::setSystemSetup);

        }
        consumer.setCreatedBy("Admin");
        consumer.setCreatedAt(LocalDateTime.now());
        consumer.setStatus(1);
        Consumer savedConsumer = consumerRepository.save(consumer);
        return savedConsumer.toDTO();
    }

    @Transactional
    public Optional<ConsumerDTO> updateConsumer(Long id, ConsumerDTO consumerDTO) {
        return consumerRepository.findById(id)
                .map(existingConsumerData -> {
                    existingConsumerData.setName(consumerDTO.getName());
                    existingConsumerData.setDescription(consumerDTO.getDescription());
                    existingConsumerData.setPlannerType(consumerDTO.getPlannerType());
                    existingConsumerData.setTrigger(consumerDTO.getTrigger());
                    existingConsumerData.setLastModifiedAt(LocalDateTime.now());
                    existingConsumerData.setLastModifiedBy("Admin");
                    if (consumerDTO.getFunds() != null) {
                        existingConsumerData.getFunds().clear();
                        List<Fund> funds = consumerDTO.getFunds().stream()
                                .map(fundDTO -> {
                                    Fund fund;
                                    if(fundDTO.getId() != null) {
                                        fund = fundRepository.findById(fundDTO.getId()).orElseGet(Fund::new);
                                    } else {
                                        fund = new Fund();
                                    }
                                    fund.setName(fundDTO.getName());
                                    if (fundDTO.getAliases() != null) {
                                        fund.getAliases().clear();
                                        List<FundAlias> aliases = fundDTO.getAliases().stream()
                                                .map(FundAlias::new)
                                                .toList();
                                        aliases.forEach(alias -> alias.setFund(fund));
                                        fund.getAliases().addAll(aliases);
                                    } else {
                                        fund.getAliases().clear();
                                    }
                                    return fund;
                                })
                                .toList();
                        existingConsumerData.getFunds().addAll(funds);
                    }

                    if (consumerDTO.getSystemSetupId() != null) {
                        Optional<SystemSetup> systemSetupId = systemSetupRepository.findById(consumerDTO.getSystemSetupId());
                        systemSetupId.ifPresent(existingConsumerData::setSystemSetup);
                    } else {
                        existingConsumerData.setSystemSetup(null);
                    }

                    Consumer consumerUpdate = consumerRepository.save(existingConsumerData);
                    return consumerUpdate.toDTO();
                });
    }

    public ConsumerDTO deleteConsumer(Long id) {
        Optional<Consumer> detail = consumerRepository.findById(id);
        return detail.map(consumer -> {
            consumer.setStatus(0);
            consumer.setLastModifiedBy("Admin");
            consumer.setLastModifiedAt(LocalDateTime.now());
            return consumerRepository.save(consumer).toDTO();
        }).orElse(null);
    }

}
