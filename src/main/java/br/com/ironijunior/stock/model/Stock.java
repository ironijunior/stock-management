package br.com.ironijunior.stock.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Stock {

    @Getter
    @Setter
    @Id
    private String id;

    @Getter
    @Setter
    private Integer quantity;

    @Getter
    @Setter
    private ZonedDateTime timestamp;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonFormat()
    private Product product;

    public Stock() {
    }

    public Stock(String id, Integer quantity, ZonedDateTime timestamp, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.product = product;
    }
}
