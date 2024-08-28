package com.compass.reinan.api_ecommerce.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales")
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_sale")
    private Instant dateSale;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_cpf")
    private User user;

    @OneToMany(mappedBy = "id.sale", cascade = CascadeType.ALL)
    private Set<ItemSale> items = new HashSet<>();

    private List<Product> getProductsSales(){
        List<Product> products = new ArrayList<>();
        for(ItemSale x : items) {
            products.add(x.getProduct());
        }
        return products;
    }
}
