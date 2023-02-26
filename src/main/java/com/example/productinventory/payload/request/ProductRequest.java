package com.example.productinventory.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductRequest {
    private String productName;
    private String manufacturer;
    private  String status;
    private  int quantityInStore;
    private BigDecimal price;
}
