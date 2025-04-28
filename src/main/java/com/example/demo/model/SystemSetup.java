package com.example.demo.model;

import com.example.demo.dto.SystemSetupDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "system_setup")
@Data
@NoArgsConstructor
public class SystemSetup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String baseUrl;

    private String authenticationMethod;

    private String key;

    private String value;

    private String authenticationPlace;

    @OneToMany(mappedBy = "systemSetup")
    private List<Consumer> consumer;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedAt;

    private LocalDateTime createdAt;

    private String createdBy;

    private int status;

    // Method to map Entity to DTO
    public SystemSetupDTO toDTO(){
        return new SystemSetupDTO(
                this.id,
                this.name,
                this.baseUrl,
                this.authenticationMethod,
                this.key,
                this.value,
                this.authenticationPlace,
                this.lastModifiedBy,
                this.lastModifiedAt,
                this.createdAt,
                this.createdBy,
                this.status
        );
    }
}
