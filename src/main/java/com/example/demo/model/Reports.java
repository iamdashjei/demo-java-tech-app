//package com.example.demo.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Entity
//@Table(name = "reports")
//@Data
//@NoArgsConstructor
//public class Reports {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String type;
//
//    @ManyToMany(mappedBy = "reports")
//    private List<Consumer> consumers;
//
//    public Reports(String name, String type) {
//        this.name = name;
//        this.type = type;
//    }
//
//}
