package br.com.ironijunior.stock.builder;

import br.com.ironijunior.stock.model.Product;
import br.com.ironijunior.stock.model.Stock;

public class ProductBuilder {

	private Product product = new Product();
	
	private ProductBuilder() {
    }

    public static ProductBuilder get() {
        return new ProductBuilder();
    }

    public Product build() {
        return product;
    }

    public ProductBuilder withId() {
        product.setId("vegetable-123");
        return this;
    }

    public ProductBuilder withId(String id) {
        product.setId(id);
        return this;
    }

    public ProductBuilder withStockLastYear() {
        Stock stock = StockBuilder.get()
        		.withId("0001")
        		.withProduct(product)
        		.withQuantity(200)
        		.atLastYear().build();
    	product.setStock(stock);
        return this;
    }
    
    public ProductBuilder withStock2023() {
        Stock stock = StockBuilder.get()
        		.withId("0001")
        		.withProduct(product)
        		.withQuantity(200)
        		.at2023().build();
    	product.setStock(stock);
        return this;
    }
    
    public ProductBuilder withStockSpecific() {
        Stock stock = StockBuilder.get()
        		.withId("0001")
        		.withProduct(product)
        		.withQuantity(200)
        		.atSpecific().build();
    	product.setStock(stock);
        return this;
    }
    
    public ProductBuilder withStock(Stock stock) {
        product.setStock(stock);
        return this;
    }

	
}
