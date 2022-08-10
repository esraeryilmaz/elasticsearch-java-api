package com.example.first.demo.productsDemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {

	@GetMapping("/")	// ilk adresi buna veriyor gibi
	public String get() {
		return "Laptop";
	}

	@GetMapping("/products")
	public String get2() {
		return "Laptop 2";
	}

}
