package com.example.demo.dto;


import com.example.demo.model.SystemSetup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemSetupDTO {
    private Long id;

    private String name;

    private String baseUrl;

    private String authenticationMethod;

    private String key;

    private String value;

    private String authenticationPlace;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedAt;

    private LocalDateTime createdAt;

    private String createdBy;

    private int status;

    // Method to map DTO to Entity
    public SystemSetup toEntity(){
        SystemSetup systemSetup = new SystemSetup();
        systemSetup.setName(this.name);
        systemSetup.setBaseUrl(this.baseUrl);
        systemSetup.setAuthenticationMethod(this.authenticationMethod);
        systemSetup.setKey(this.key);
        systemSetup.setValue(this.value);
        systemSetup.setAuthenticationPlace(this.authenticationPlace);
        systemSetup.setLastModifiedBy(this.lastModifiedBy);
        systemSetup.setLastModifiedAt(this.lastModifiedAt);
        systemSetup.setCreatedAt(this.createdAt);
        systemSetup.setCreatedBy(this.createdBy);
        systemSetup.setStatus(this.status);
        return systemSetup;
    }
}
