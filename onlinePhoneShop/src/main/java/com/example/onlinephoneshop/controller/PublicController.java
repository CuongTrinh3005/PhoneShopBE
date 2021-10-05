package com.example.onlinephoneshop.controller;

import com.example.onlinephoneshop.dto.AccessoryDTO;
import com.example.onlinephoneshop.dto.PhoneDTO;
import com.example.onlinephoneshop.entity.*;
import com.example.onlinephoneshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public")
public class PublicController {
	@Autowired
	UserService userService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	BrandService brandService;

	@Autowired
	ManufacturerService manufacturerService;

	@Autowired
	PhoneService phoneService;

	@Autowired
	AccessoryService accessoryService;

	@Autowired
	PaymentService paymentService;

	@GetMapping("categories")
	public List<Category> getAllCategories(){
		return categoryService.getAllCategories();
	}

	@GetMapping("categories/{id}")
	public Optional<Category> retrieveCategory(@PathVariable String id) {
		return categoryService.findById(id);
	}

	@GetMapping("categories/search")
	public Optional<Category> getCategoryByName(@RequestParam String name) {
		return categoryService.findCategoryByName(name);
	}

	@GetMapping("brands")
	public List<Brand> getAllBrands(){
		return brandService.getAllBrands();
	}

	@GetMapping("brands/{id}")
	public Optional<Brand> retrieveBrand(@PathVariable String id) {
		return brandService.findById(id);
	}

	@GetMapping("brands/search")
	public Optional<Brand> getBrandByName(@RequestParam String name) {
		return brandService.findBrandByName(name);
	}

	@GetMapping("manufacturers")
	public List<Manufacturer> getAllManufacturers(){
		return manufacturerService.getAllManufacturers();
	}

	@GetMapping("manufacturers/{id}")
	public Optional<Manufacturer> retrieveManufacturer(@PathVariable Long id) {
		return manufacturerService.findById(id);
	}

	@GetMapping("manufacturers/search")
	public Optional<Manufacturer> getManufacturerByName(@RequestParam String name) {
		return manufacturerService.findManufacturerByName(name);
	}

	@GetMapping("products")
	public List<Phone> getAllProducts(){
		return phoneService.getAllProducts();
	}

	@GetMapping("products/{productId}")
	public Object getProductById(@PathVariable String productId) throws Throwable {
		return phoneService.getProductById(productId).get();
	}

	@GetMapping("payments")
	public List<Payment> getAllPayments(){
		return paymentService.getAllPayments();
	}

	@GetMapping("payments/{id}")
	public Payment getAllPayments(@PathVariable Integer id){
		return paymentService.getById(id);
	}
}