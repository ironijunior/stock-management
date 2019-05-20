package br.com.ironijunior.stock.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ironijunior.stock.model.ProductSale;

public interface ProductSaleRepository extends JpaRepository<ProductSale, Long> {
	
	@Query(value = "SELECT id, product_id, SUM(quantity_sold) as quantity_sold, timestamp "
			+ "       FROM Product_Sale "
			+ "      WHERE timestamp >= :initialDate and timestamp < :finalDate "
            + "      GROUP BY product_id "
            + "      ORDER BY quantity_sold desc LIMIT :limit", nativeQuery = true)
    public List<ProductSale> findTopThreeSoldProducts(
    		@Param("initialDate") ZonedDateTime initialDate, 
    		@Param("finalDate") ZonedDateTime finalDate, 
    		@Param("limit") int limit);
	
}
