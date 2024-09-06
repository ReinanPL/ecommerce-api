package com.compass.reinan.api_ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_item_sales")
public class ItemSale implements Serializable {

    @EmbeddedId
    private ItemSalePK id = new ItemSalePK();
    private Integer quantity;
    private BigDecimal price;

    public Sale getSale() {
        return id.getSale();
    }

    public Product getProduct() {
        return id.getProduct();
    }


}
