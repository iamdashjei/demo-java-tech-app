//package com.example.demo.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Entity
//@Table(name = "sources")
//@Data
//@NoArgsConstructor
//public class Source {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @ManyToMany(mappedBy = "sources")
//    private List<Consumer> consumers;
//
//    public Source(String name) {
//        this.name = name;
//    }
//}
