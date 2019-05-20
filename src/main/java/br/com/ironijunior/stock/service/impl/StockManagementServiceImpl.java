package br.com.ironijunior.stock.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ironijunior.stock.dto.ProductDTO;
import br.com.ironijunior.stock.dto.ProductSaleDTO;
import br.com.ironijunior.stock.dto.StatisticsDTO;
import br.com.ironijunior.stock.dto.StockDTO;
import br.com.ironijunior.stock.exception.NotFoundException;
import br.com.ironijunior.stock.exception.OutdatedException;
import br.com.ironijunior.stock.model.Product;
import br.com.ironijunior.stock.model.ProductSale;
import br.com.ironijunior.stock.model.Stock;
import br.com.ironijunior.stock.model.enumeration.StatisticsTimeEnum;
import br.com.ironijunior.stock.repository.ProductRepository;
import br.com.ironijunior.stock.repository.ProductSaleRepository;
import br.com.ironijunior.stock.repository.StockRepository;
import br.com.ironijunior.stock.service.StockManagementService;
import br.com.ironijunior.stock.util.ConversorUtil;

@Service
public class StockManagementServiceImpl implements StockManagementService {

	@Autowired
	private StockRepository stockRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProductSaleRepository productSaleRepo;
	
	@Override
	public void updateStock(StockDTO stockDTO) throws OutdatedException {
		Product product = getProduct(stockDTO.getProductId());
		
		synchronized(product) {
            Stock stock = product.getStock();
            
            if(stock != null) {
            	checkOutdatedStock(stock, stockDTO);
            	createProductSale(stock, stockDTO);            	
            }
            
            Stock newStock = stockDTO.getStock(product);
            stockRepo.save(newStock);
            product.setStock(newStock);
            productRepo.save(product);
        }
	}

	@Override
	public ProductDTO getCurrentStockFromProduct(String productId) throws NotFoundException {
		Optional<Product> optProduct = productRepo.findById(productId);
        optProduct.orElseThrow(() -> new NotFoundException());
        return new ProductDTO(optProduct.get());
	}
	
	@Override
	public StatisticsDTO getStatistics(StatisticsTimeEnum time) {
		ZonedDateTime initialDate = null;
        ZonedDateTime finalDate = null;

        if (StatisticsTimeEnum.lastMonth.equals(time)) {
        	finalDate = ZonedDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.MIDNIGHT, ZoneId.of("UTC"));
            initialDate = finalDate.minusMonths(1);
        } else {
        	initialDate = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of("UTC"));
            finalDate = ZonedDateTime.now();
        }
        
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setRequestTimestamp(ZonedDateTime.now());
        statisticsDTO.setRange(time);
        statisticsDTO.setTopAvailableProducts(getTopProductsAvailable(time, initialDate, finalDate, 3));
        statisticsDTO.setTopSellingProducts(getTopSoldProducts(time, initialDate, finalDate, 3));
        return statisticsDTO;
	}
	
	private Product getProduct(String productId) {
		Optional<Product> optProduct = productRepo.findById(productId);
		return optProduct.isPresent() ? optProduct.get() : productRepo.saveAndFlush(new Product(productId));
	}
	
	private void checkOutdatedStock(Stock current, StockDTO newer) {
		if(current.getTimestamp().isAfter(newer.getTimestamp())) {
			throw new OutdatedException();
		}
	}

	private void createProductSale(Stock current, StockDTO newer) {
		if(current.getQuantity() > newer.getQuantity()) {
			Integer diff = current.getQuantity() - newer.getQuantity();
			
			ProductSale sale = new ProductSale(current.getProduct(), diff, newer.getTimestamp());
			productSaleRepo.saveAndFlush(sale);
		}
	}
	
	private List<StockDTO> getTopProductsAvailable(StatisticsTimeEnum time, ZonedDateTime initialDate, ZonedDateTime finalDate, int limit) {
		List<Stock> stocks = stockRepo.getTopProductsAvailable(initialDate, finalDate, limit);
		return ConversorUtil.convertStockToDTO(stocks);
    }

    private List<ProductSaleDTO> getTopSoldProducts(StatisticsTimeEnum time, ZonedDateTime initialDate, ZonedDateTime finalDate, int limit) {
        List<ProductSale> sales = productSaleRepo.findTopThreeSoldProducts(initialDate, finalDate, limit);
        return ConversorUtil.convertProductSalesToDTO(sales);
    }
}
