package br.com.ironijunior.stock.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ironijunior.stock.dto.ProductDTO;
import br.com.ironijunior.stock.dto.StatisticsDTO;
import br.com.ironijunior.stock.dto.StockDTO;
import br.com.ironijunior.stock.exception.NotFoundException;
import br.com.ironijunior.stock.exception.OutdatedException;
import br.com.ironijunior.stock.model.enumeration.StatisticsTimeEnum;
import br.com.ironijunior.stock.service.StockManagementService;

@RestController
public class StockManagementController {
	
	@Autowired
	private StockManagementService service;
	
	@PostMapping(path="/updateStock")
	public ResponseEntity<?> create(@Valid @RequestBody StockDTO request) {
		try {
			service.updateStock(request);
		} catch(OutdatedException ex) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(path="/stock")
	public ResponseEntity<ProductDTO> getStockFromProduct(
			@RequestParam(value="productId", required=true) String productId) {
		
		ProductDTO product;
		try {
			product = service.getCurrentStockFromProduct(productId);
		} catch(NotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.build();
		}
		
		return new ResponseEntity<ProductDTO>(product, HttpStatus.OK);
	}
	
	@GetMapping(path="/statistics")
	public ResponseEntity<StatisticsDTO> getStatistics(
			@RequestParam(value="time", required=true) StatisticsTimeEnum time) {
		
		try {
			return ResponseEntity.ok(service.getStatistics(time));
		} catch(NotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
}
