package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fund_aliases")
@Data
@NoArgsConstructor
public class FundAlias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "fund_id")
    private Fund fund;

    public FundAlias(String value) {
        this.value = value;
    }

}
