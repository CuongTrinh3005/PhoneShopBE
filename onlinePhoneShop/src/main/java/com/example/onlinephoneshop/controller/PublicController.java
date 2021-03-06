package com.example.onlinephoneshop.controller;

import com.example.onlinephoneshop.dto.AccessoryDTO;
import com.example.onlinephoneshop.dto.PhoneDTO;
import com.example.onlinephoneshop.dto.ProductDTO;
import com.example.onlinephoneshop.entity.*;
import com.example.onlinephoneshop.payload.request.SimilarProductListIds;
import com.example.onlinephoneshop.service.*;
import com.example.onlinephoneshop.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

	@Autowired
	RatingService ratingService;

	@Autowired
	ViewHistoryService viewHistoryService;

	@GetMapping("check-user-viewing-exist/{userId}")
	public Boolean checkUserViewingExisted(@PathVariable String userId){
		return viewHistoryService.existedByUserId(userId);
	}

	@GetMapping("check-product-viewing-exist/{productId}")
	public Boolean checkProductViewingExisted(@PathVariable String productId){
		return viewHistoryService.existedByProductId(productId);
	}

	@GetMapping("reset-password")
	public Boolean resetPassword(@RequestParam String username){
		return userService.resetPassword(username.trim());
	}

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
	public List<Object> getAllProducts(){
		List<Object> productList = phoneService.getAllProducts();
		List<Object> productDTOList = new ArrayList<>();
		for (Object product: productList) {
			ProductDTO dto = phoneService.convertEntityToProductDTO((Product) product);
			productDTOList.add(dto);
		}
		return productDTOList;
	}

	@GetMapping("products/option={option}")
	public List<Object> getAllProducts(@PathVariable Integer option){
		return phoneService.getAllProductsWithOrder(option);
	}

	@PostMapping("products/list-ids")
	public List<Object> getAllProductsByIds(@RequestBody SimilarProductListIds ids){
		List<Object> productList = phoneService.getByListIds(ids.getSimilarProductIds());
		List<Object> productDTOList = new ArrayList<>();
		for (Object product: productList) {
			ProductDTO dto = phoneService.convertEntityToProductDTO((Product) product);
			productDTOList.add(dto);
		}
		return productDTOList;
	}

	@GetMapping("imei")
	public String getIMEINo(){
		String imeiNo = Helper.generateIMEI();
		while(phoneService.existByImeiNo(imeiNo)){
			imeiNo = Helper.generateIMEI();
		}
		return imeiNo;
	}

	@GetMapping("products/{productId}")
	public Object getProductById(@PathVariable String productId) throws Throwable {
		Object object = phoneService.getProductById(productId).get();
		if(object instanceof Phone)
			return phoneService.convertEntityToDTO((Phone) object);
		else return accessoryService.convertEntityToDTO((Accessory) object);
	}

	@GetMapping("products/{productId}/list-accessories")
	public Set<AccessoryDTO> getAccessoriesOfphoneId(@PathVariable String productId) throws Throwable {
		return phoneService.getAllAccessoriesOfPhone(productId);
	}

	@GetMapping("products/accessories")
	public List<AccessoryDTO> getAllAccessories(){
		return accessoryService.getAllAccessories().stream().map(accessoryService::convertEntityToDTO).collect(Collectors.toList());
	}

	@GetMapping("ratings/products/{id}")
	public List<Rating> getProductRatings(@PathVariable String id){
		return ratingService.getAllRatingByProductId(id);
	}

	public List<Object> convertToListDTO(List<Object> objectList){
		List<Object> dtoList = new ArrayList<>();
		for (Object object: objectList) {
			object = phoneService.convertEntityToProductDTO((Product) object);
			dtoList.add(object);
		}
		return dtoList;
	}

	@GetMapping("products/search")
	public List<Object> getProductByName(@RequestParam String productName) throws Throwable {
		return phoneService.getProductByName(productName);
	}

	@GetMapping("products/embedded-search")
	public List<Object> getProductByNameIgnoreCaseContaining(@RequestParam String productName) throws Throwable {
		return phoneService.getProductByNameIgnoreCaseContaining(productName);
	}

	@GetMapping("products/category/{id}")
	public List<Object> getProductByCategoryId(@PathVariable String id) throws Throwable {
		return phoneService.getProductByCategoryId(id);
	}

	@GetMapping("products/top-view")
	public List<Object> getProductTopView() throws Throwable {
		return phoneService.getTop10MostView();
	}

	@GetMapping("products/top-discount")
	public List<Object> getProductTopDiscount() throws Throwable {
		return phoneService.getTop10MostDiscount();
	}

	@GetMapping("products/top-newest")
	public List<Object> getProductNewest() throws Throwable {
		return phoneService.getTop10Newest();
	}

	@GetMapping("products/best-seller/limit/{num}")
	public List<Object> getProductBestSeller(@PathVariable Integer num) throws Throwable {
		return phoneService.getTop10BestSeller(0, num);
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