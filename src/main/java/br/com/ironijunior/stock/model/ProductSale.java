package br.com.ironijunior.stock.model;


import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class ProductSale {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Getter
    @Setter
    private Integer quantitySold;

    @Getter
    @Setter
    private ZonedDateTime timestamp;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    public ProductSale() {
    }
    
    public ProductSale(Long id, String productId, Integer itemsSold) {
    	this.product = new Product(productId);
    	this.quantitySold = itemsSold;
    }

    public ProductSale(Product product, Integer quantitySold, ZonedDateTime timestamp) {
        this.quantitySold = quantitySold;
        this.timestamp = timestamp;
        this.product = product;
    }


}
