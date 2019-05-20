package br.com.ironijunior.stock.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.ironijunior.stock.model.Product;
import br.com.ironijunior.stock.model.Stock;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockDTO implements Serializable {

	@NotNull
	@Getter
    @Setter
    private String id;

	@NotNull
	@Getter
	@Setter
	private ZonedDateTime timestamp;
	
	@NotNull
	@Getter
    @Setter
    private String productId;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    @Getter
    @Setter
    private Integer quantity;
    
	public StockDTO() {
		
	}
	
    public StockDTO(Stock stock) {
    	this.id = stock.getId();
    	this.productId = stock.getProduct().getId();
    	this.quantity = stock.getQuantity();
        this.timestamp = stock.getTimestamp();
    }

    public Stock getStock(Product product) {
    	return new Stock(id, quantity, timestamp, product);
    }
    
    public StockDTO(@NotNull String id, 
    		@NotNull ZonedDateTime timestamp, 
    		@NotNull String productId, 
    		@NotNull Integer quantity) {
        
    	this.id = id;
        this.timestamp = timestamp;
        this.productId = productId;
        this.quantity = quantity;
    }
}
