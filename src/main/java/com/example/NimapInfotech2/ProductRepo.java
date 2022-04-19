package com.example.NimapInfotech2;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

	
	List<Product> findByPrice(long price);
	List<Product> findByName (String name);
	
}
