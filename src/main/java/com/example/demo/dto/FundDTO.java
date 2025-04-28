package com.example.demo.dto;

import com.example.demo.model.Fund;
import com.example.demo.model.FundAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundDTO {

    private Long id;

    private String name;

    private List<String> aliases;

    public Fund toEntity() {
        Fund fund = new Fund();
        fund.setId(this.id);
        fund.setName(this.name);
        if (this.aliases != null && !this.aliases.isEmpty()) {
            List<FundAlias> fundAliases = this.aliases.stream()
                    .map(FundAlias::new)
                    .toList();
            fundAliases.forEach(alias -> alias.setFund(fund));
            fund.setAliases(fundAliases);
        }
        return fund;
    }
}
