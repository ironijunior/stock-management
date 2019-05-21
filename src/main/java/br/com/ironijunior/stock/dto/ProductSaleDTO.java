package br.com.ironijunior.stock.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ironijunior.stock.model.ProductSale;
import lombok.Getter;
import lombok.Setter;

public class ProductSaleDTO implements Serializable {

    @Getter
    @Setter
    private String productId;

    @Getter
    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer itemsSold;

    public ProductSaleDTO() {
    }

    public ProductSaleDTO(ProductSale productSale) {
        this.productId = productSale.getProduct().getId();
        this.itemsSold = productSale.getQuantitySold();
    }
    
    public ProductSaleDTO(String productId, Integer itemsSold) {
    	this.productId = productId;
    	this.itemsSold = itemsSold;
    }

}
