package com.compass.reinan.api_ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true, nullable = false, length =50)
    private String name;
    @Column(name = "active")
    private Boolean active = true;
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();;

}
