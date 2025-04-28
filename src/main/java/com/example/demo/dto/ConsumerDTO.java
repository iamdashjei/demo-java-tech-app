package com.example.demo.dto;

import com.example.demo.model.Consumer;
import com.example.demo.model.Fund;
import com.example.demo.model.SystemSetup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerDTO {

    private String name;

    private String description;

    private String plannerType;

    private List<String> trigger;

    private List<FundDTO> funds;

    private Long systemSetupId;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedAt;

    private LocalDateTime createdAt;

    private String createdBy;

    private int status;

    public Consumer toEntity(){
        Consumer consumer = new Consumer();
        consumer.setName(this.name);
        consumer.setDescription(this.description);
        consumer.setPlannerType(this.plannerType);
        consumer.setTrigger(this.trigger);
        consumer.setLastModifiedBy(this.lastModifiedBy);
        consumer.setLastModifiedAt(this.lastModifiedAt);
        consumer.setCreatedAt(this.createdAt);
        consumer.setCreatedBy(this.createdBy);
        consumer.setStatus(this.status);
        if (this.funds != null && !this.funds.isEmpty()) {
            List<Fund> result = this.funds.stream()
                    .map(FundDTO::toEntity)
                    .toList();
            consumer.setFunds(result);
        } else {
            consumer.setFunds(new ArrayList<>());
        }

        return consumer;
    }
}
