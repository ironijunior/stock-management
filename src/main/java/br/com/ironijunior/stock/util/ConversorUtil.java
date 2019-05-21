package br.com.ironijunior.stock.util;

import java.util.List;
import java.util.stream.Collectors;

import br.com.ironijunior.stock.dto.ProductSaleDTO;
import br.com.ironijunior.stock.dto.StockDTO;
import br.com.ironijunior.stock.model.ProductSale;
import br.com.ironijunior.stock.model.Stock;

public final class ConversorUtil {

	private ConversorUtil() {
		
	}
	
	public static List<ProductSaleDTO> convertProductSalesToDTO(List<ProductSale> sales) {
		return sales.stream().map(ProductSaleDTO::new).collect(Collectors.toList());
	}
	
	public static List<StockDTO> convertStockToDTO(List<Stock> stocks) {
		return stocks.stream().map(StockDTO::new).collect(Collectors.toList());
	}
}
