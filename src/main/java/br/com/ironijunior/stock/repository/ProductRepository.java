package br.com.ironijunior.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ironijunior.stock.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	
}
