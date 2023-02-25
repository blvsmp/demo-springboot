package com.example.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductModel>> getAllProducts() {
		return new ResponseEntity<List<ProductModel>>(productRepository.findAll(), HttpStatus.OK);			
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductModel> getOneProduct(@PathVariable(value="id") UUID id) {
		Optional<ProductModel> product = productRepository.findById(id);

		if (product.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ProductModel>(product.get(), HttpStatus.OK);
	}

	@PostMapping("/products")
	public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductModel product) {
		return new ResponseEntity<ProductModel>(productRepository.save(product), HttpStatus.CREATED);
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable(value="id") UUID id) {
		Optional<ProductModel> product = productRepository.findById(id);

		if (product.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		productRepository.delete(product.get());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<ProductModel> updateProduct(@PathVariable(value="id") UUID id,
													  @RequestBody @Valid ProductModel product) {
		Optional<ProductModel> obj = productRepository.findById(id);

		if (obj.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		product.setIdProduct(obj.get().getIdProduct());
		return new ResponseEntity<ProductModel>(productRepository.save(product), HttpStatus.OK);
	}
}
