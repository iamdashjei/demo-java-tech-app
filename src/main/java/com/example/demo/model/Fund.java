package com.example.demo.model;

import com.example.demo.dto.FundDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "funds")
@Data
@NoArgsConstructor
public class Fund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "fund", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FundAlias> aliases;

    public FundDTO toDTO() {
        List<String> aliasValueList = null;
        if (this.aliases != null) {
            aliasValueList = this.aliases.stream().map(FundAlias::getValue).toList();
        }
        return new FundDTO();
    }


}
