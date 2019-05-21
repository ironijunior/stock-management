package br.com.ironijunior.stock.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ironijunior.stock.model.Stock;

public interface StockRepository extends JpaRepository<Stock, String> {
	
	@Query(value = "SELECT t2.id "
			+ "          , t2.quantity "
			+ "          , MAX(t2.timestamp) as timestamp "
			+ "          , t2.product_id "
			+ "       FROM (SELECT product_id "
			+ "                  , MAX(quantity) as maxq "
			+ "				  FROM stock "
			+ "				 WHERE timestamp >= :initialDate "
			+ "                AND timestamp < :finalDate "
			+ "              GROUP BY product_id "
			+ "              ORDER BY maxq desc "
			+ "              LIMIT :limit ) t1 "
            + "      INNER JOIN "
            + "            (SELECT * "
            + "			      FROM Stock "
            + "              WHERE timestamp >= :initialDate "
            + "                AND timestamp < :finalDate) t2 " 
            + "         ON t1.product_id = t2.product_id "
            + "        AND t1.maxq = t2.quantity "
            + "      GROUP BY t2.product_id", nativeQuery = true)
	public List<Stock> getTopProductsAvailable(
			@Param("initialDate") ZonedDateTime initialDate, 
			@Param("finalDate") ZonedDateTime finalDate,
			@Param("limit") int limit);
	
}
