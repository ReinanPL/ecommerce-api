package com.compass.reinan.api_ecommerce.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Addresses")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cep", nullable = false, length =8)
    private String cep;
    @Column(name = "city", nullable = false, length =50)
    private String city;
    @Column(name = "state", nullable = false, length =50)
    private String state;
    @Column(name = "street", nullable = false, length =100)
    private String street;
    @Column(name = "number", nullable = false, length =10)
    private String number;
    @Column(name = "complement", nullable = false, length =200)
    private String complement;
}
