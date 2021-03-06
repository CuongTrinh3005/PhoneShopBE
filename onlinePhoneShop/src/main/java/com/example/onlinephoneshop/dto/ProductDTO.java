package com.example.onlinephoneshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String productId;
    @NotBlank
    private String productName;

    @DecimalMin(value = "0", message = "Price must be not under 0")
    private Float unitPrice;

    @DecimalMin(value = "0", message = "Quantity must be not under 0")
    private Long quantity;
    @DecimalMin(value = "0", message = "Discount must be not under 0%")
    @DecimalMax(value = "0.7", message = "Discount must be not over 70%")
    private Float discount;
    private byte[] image;
    private String description;
    private String specification;
    @DecimalMin(value = "0", message = "No. view must be not under 0")
    private Long viewCount;
    private Boolean special;
    private Boolean available;

    @DecimalMin(value = "0", message ="Number of month in warranty must greater than 0")
    private Integer warranty;

    private Integer label;

    @NotBlank
    private String categoryName;

    @NotBlank
    private String manufacturerName;

    @NotBlank
    private String brandName;
}