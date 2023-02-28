package com.example.productinventory.payload.response;

import com.example.productinventory.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String productName;
    private String manufacturer;
    private  String status;
    private  int quantityInStore;
    private BigDecimal price;

    public ProductResponse(long id, String test_product, double v) {
    }
}
