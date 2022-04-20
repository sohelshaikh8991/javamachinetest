package com.example.NimapInfotech2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ProductController {
    
	@Autowired
	ProductRepo productrepo;
	@GetMapping("/products?page=2")
	public ResponseEntity<List<Product>>getAllProducts(@RequestParam(required=false)String name){
		try {
			List<Product> products = new ArrayList<Product>();
			if(name == null)
				productrepo.findAll().forEach(products::add);
			else
				productrepo.findByName(name).forEach(products::add);
			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(products,HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<> (null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductsById(@PathVariable("id")long id){
		Optional<Product> productdata = productrepo.findById(id);
		if (productdata.isPresent()) {
			return new ResponseEntity<>(productdata.get(),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		try {
			Product product1 = productrepo.save(new Product(product.getName(),product.getDescription(),123l));
			
			return new ResponseEntity<>(product1,HttpStatus.CREATED);
		} catch (Exception e) {
		    return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/products/{id}")
	
	public ResponseEntity<Product>updateProduct(@PathVariable("id")long id,@RequestBody Product product){
		Optional<Product>productdata = productrepo.findById(id);
		if(productdata.isPresent()) {
		Product product1 = productdata.get();
		product1.setName(product.getName());
		product1.setDesciption(product.getDescription());
		product1.setPrice(product.getPrice());
		return new ResponseEntity<>(productrepo.save(product1),HttpStatus.OK);
	}else
	{
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	}
    @DeleteMapping("/products/{id}")
    public ResponseEntity <HttpStatus> deleteProducts(@PathVariable("id")long id){
    	try {
			productrepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} catch (Exception e) {
		     return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
    }
}
    
    
    
    
    
    
    
    
    
    
