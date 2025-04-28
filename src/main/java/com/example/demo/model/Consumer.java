package com.example.demo.model;

import com.example.demo.dto.ConsumerDTO;
import com.example.demo.dto.FundDTO;
import com.example.demo.dto.SystemSetupDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "consumers")
@Data
@NoArgsConstructor
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String plannerType;

    @ManyToOne
    @JoinColumn(name = "system_setup_id")
    private SystemSetup systemSetup;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "consumer_fund",
            joinColumns = @JoinColumn(name = "consumer_id"),
            inverseJoinColumns = @JoinColumn(name = "fund_id")
    )
    private List<Fund> funds = new ArrayList<>();

    @ElementCollection
    private List<String> trigger;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedAt;

    private LocalDateTime createdAt;

    private String createdBy;

    private int status;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "consumer_sources",
//            joinColumns = @JoinColumn(name = "consumer_id"),
//            inverseJoinColumns = @JoinColumn(name = "source_id")
//    )
//    private List<Source> sources;
//
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "consumer_runs",
//            joinColumns = @JoinColumn(name = "consumer_id"),
//            inverseJoinColumns = @JoinColumn(name = "runs_id")
//    )
//    private List<Runs> runs;
//
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "consumer_reports",
//            joinColumns = @JoinColumn(name = "consumer_id"),
//            inverseJoinColumns = @JoinColumn(name = "reports_id")
//    )
//    private List<Reports> reports;

    public ConsumerDTO toDTO(){
        List<FundDTO> fundDTOs = null;
        if (this.funds != null) {
            fundDTOs = this.funds.stream()
                    .map(fund -> {
                        List<String> aliases = null;
                        if (fund.getAliases() != null) {
                            aliases = fund.getAliases().stream().map(FundAlias::getValue).toList();
                        }
                        return new FundDTO(fund.getId(), fund.getName(), aliases);
                    })
                    .toList();
        } else {
            funds = new ArrayList<>();
        }

        Long setupId = (this.systemSetup != null) ? this.systemSetup.getId() : null;

        return new ConsumerDTO(
                this.name,
                this.description,
                this.plannerType,
                this.trigger,
                fundDTOs,
                setupId,
                this.lastModifiedBy,
                this.lastModifiedAt,
                this.createdAt,
                this.createdBy,
                this.status
                );
    }
}
