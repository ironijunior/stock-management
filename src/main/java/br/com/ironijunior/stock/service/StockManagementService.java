package br.com.ironijunior.stock.service;

import br.com.ironijunior.stock.dto.ProductDTO;
import br.com.ironijunior.stock.dto.StatisticsDTO;
import br.com.ironijunior.stock.dto.StockDTO;
import br.com.ironijunior.stock.exception.NotFoundException;
import br.com.ironijunior.stock.exception.OutdatedException;
import br.com.ironijunior.stock.model.enumeration.StatisticsTimeEnum;

public interface StockManagementService {

	void updateStock(StockDTO productStock) throws OutdatedException;

	ProductDTO getCurrentStockFromProduct(String productId) throws NotFoundException;
	
	StatisticsDTO getStatistics(StatisticsTimeEnum time);
	
}
