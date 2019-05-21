package br.com.ironijunior.stock.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

import br.com.ironijunior.stock.model.Product;
import lombok.Getter;
import lombok.Setter;

public class ProductDTO implements Serializable {

	@Getter
	@Setter
	private String productId;
	
	@Getter
	@Setter
	private ZonedDateTime requestTimestamp;
	
	@Getter
	@Setter
	private StockDTO stock;

	public ProductDTO() {
	}

	public ProductDTO(Product product) {
	    this.productId = product.getId();
	    this.requestTimestamp = ZonedDateTime.now();
	    stock = new StockDTO(product.getStock());
	    stock.setProductId(null);
	}

}
