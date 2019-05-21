package br.com.ironijunior.stock.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ironijunior.stock.builder.ProductBuilder;
import br.com.ironijunior.stock.builder.StockBuilder;
import br.com.ironijunior.stock.dto.ProductDTO;
import br.com.ironijunior.stock.dto.StatisticsDTO;
import br.com.ironijunior.stock.dto.StockDTO;
import br.com.ironijunior.stock.exception.DuplicatedStockException;
import br.com.ironijunior.stock.exception.NotFoundException;
import br.com.ironijunior.stock.exception.OutdatedException;
import br.com.ironijunior.stock.model.Product;
import br.com.ironijunior.stock.model.Stock;
import br.com.ironijunior.stock.model.enumeration.StatisticsTimeEnum;
import br.com.ironijunior.stock.repository.ProductRepository;
import br.com.ironijunior.stock.repository.ProductSaleRepository;
import br.com.ironijunior.stock.repository.StockRepository;
import br.com.ironijunior.stock.service.StockManagementService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockManagementServiceImplTest {

	@Autowired
	private StockManagementService service;
	
	@MockBean
	private StockRepository stockRepo;
	
	@MockBean
	private ProductRepository productRepo;
	
	@MockBean
	private ProductSaleRepository productSaleRepo;

	@Rule
	public ExpectedException exp = ExpectedException.none();
	
	@Test
	public void updateStockOfAProductWithoutOtherStock() {
		Stock stock = StockBuilder.get()
				.withId()
				.withQuantity()
				.withProduct()
				.atSpecific().build();
		
		Mockito.when(stockRepo.findById(Mockito.anyString()))
			.thenReturn(Optional.ofNullable(null));
		
		Mockito.when(productRepo.findById(Mockito.anyString()))
			.thenReturn(Optional.of(stock.getProduct()));
		
		service.updateStock(new StockDTO(stock));
		
		assertNotNull(stock.getProduct());
		
	}
	
	@Test
	public void updateStockOfAProductWithOtherStock() {
		Product product = ProductBuilder.get()
				.withId()
				.withStockLastYear().build();
		Stock stock = StockBuilder.get()
				.withId()
				.withQuantity()
				.withProduct(product)
				.at2023().build();
		
		Mockito.when(stockRepo.findById(Mockito.anyString()))
			.thenReturn(Optional.ofNullable(null));
		
		Mockito.when(productRepo.findById(Mockito.anyString()))
			.thenReturn(Optional.of(stock.getProduct()));
		
		service.updateStock(new StockDTO(stock));
		
		assertNotNull(stock.getProduct());
		assertEquals(stock.getQuantity(), product.getStock().getQuantity());
	}
	
	@Test
	public void updateStockOfAProductWithDuplicatedStock() {
		Product product = ProductBuilder.get()
				.withId()
				.withStockLastYear().build();
		Stock stock = StockBuilder.get()
				.withId()
				.withQuantity()
				.withProduct(product)
				.at2023().build();
		
		Mockito.when(stockRepo.findById(Mockito.anyString()))
			.thenReturn(Optional.of(stock));
		
		Mockito.when(productRepo.findById(Mockito.anyString()))
			.thenReturn(Optional.of(stock.getProduct()));
		
		exp.expect(DuplicatedStockException.class);
		
		service.updateStock(new StockDTO(stock));
	}
	
	@Test
	public void updateStockOfAProductWithOutdatedStock() {
		Product product = ProductBuilder.get()
				.withId()
				.withStock2023().build();
		Stock stock = StockBuilder.get()
				.withId()
				.withQuantity()
				.withProduct(product)
				.atLastYear().build();
		
		Mockito.when(stockRepo.findById(Mockito.anyString()))
			.thenReturn(Optional.ofNullable(null));
		
		Mockito.when(productRepo.findById(Mockito.anyString()))
			.thenReturn(Optional.of(stock.getProduct()));
		
		exp.expect(OutdatedException.class);
		
		service.updateStock(new StockDTO(stock));
		
	}

	@Test
	public void testGetCurrentStockFromProduct() {
		String productId = "product-1";
		
		Product product = ProductBuilder.get()
				.withId(productId).build();
		
		Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product));
		
		ProductDTO dto = service.getCurrentStockFromProduct(productId);
		
		assertNotNull(dto);
		assertEquals(productId, dto.getProductId());
	}
	
	@Test
	public void testGetCurrentStockFromNonexistentProduct() {
		String productId = "product-1";
		
		Mockito.when(productRepo.findById(productId)).thenReturn(Optional.ofNullable(null));
		
		exp.expect(NotFoundException.class);
		
		service.getCurrentStockFromProduct(productId);
	}

	@Test
	public void testGetStatistics() {
		//FIXME
		StatisticsDTO stats = service.getStatistics(StatisticsTimeEnum.today);
		
		assertNotNull(stats);
	}

}
