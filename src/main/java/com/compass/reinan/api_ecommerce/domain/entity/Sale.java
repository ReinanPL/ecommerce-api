package com.compass.reinan.api_ecommerce.domain.entity;


import com.compass.reinan.api_ecommerce.domain.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_sales")
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_sale")
    private Instant dateSale;
    @ManyToOne
    @JoinColumn(name = "user_cpf")
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @JsonIgnore
    @OneToMany(mappedBy = "id.sale", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<ItemSale> items = new HashSet<>();
}
