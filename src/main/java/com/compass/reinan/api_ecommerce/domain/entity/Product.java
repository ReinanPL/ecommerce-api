package com.compass.reinan.api_ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",  unique = true, nullable = false, length =100)
    private String name;
    @Column(name = "quantity_in_stock", nullable = false)
    private Integer quantityInStock;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    private boolean active = true;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "id.product", cascade = CascadeType.ALL)
    private Set<ItemSale> items = new HashSet<>();

    private List<Sale> getSales(){
        List<Sale> sales = new ArrayList<>();
        for(ItemSale x : items) {
            sales.add(x.getSale());
        }
        return sales;
    }
}
