package br.com.ironijunior.stock.builder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import br.com.ironijunior.stock.model.Product;
import br.com.ironijunior.stock.model.Stock;

public class StockBuilder {

	private Stock stock = new Stock();
	
	private StockBuilder() {
    }

    public static StockBuilder get() {
        return new StockBuilder();
    }

    public Stock build() {
        return stock;
    }

    public StockBuilder withId() {
        stock.setId("0001");
        return this;
    }

    public StockBuilder withId(String id) {
        stock.setId(id);
        return this;
    }

    public StockBuilder withQuantity() {
        stock.setQuantity(100);
        return this;
    }
    
    public StockBuilder withQuantity(Integer quantity) {
        stock.setQuantity(quantity);
        return this;
    }
    
    public StockBuilder atNow() {
        stock.setTimestamp(Instant.now().atZone(ZoneId.of("UTC")));
        return this;
    }
    
    public StockBuilder atSpecific() {
        LocalDateTime ldt = LocalDateTime.of(2019, 01, 01, 10, 10);
    	stock.setTimestamp(ldt.atZone(ZoneId.of("UTC")));
        return this;
    }
    
    public StockBuilder atLastYear() {
        LocalDateTime ldt = LocalDateTime.of(2018, 10, 10, 10, 10);
    	stock.setTimestamp(ldt.atZone(ZoneId.of("UTC")));
        return this;
    }
    
    public StockBuilder at2023() {
        LocalDateTime ldt = LocalDateTime.of(2023, 10, 10, 10, 10);
    	stock.setTimestamp(ldt.atZone(ZoneId.of("UTC")));
        return this;
    }
    
    public StockBuilder withProduct(Product product) {
        stock.setProduct(product);
        return this;
    }
    
    public StockBuilder withProduct() {
        stock.setProduct(ProductBuilder.get().withId().build());
        return this;
    }

	
}
