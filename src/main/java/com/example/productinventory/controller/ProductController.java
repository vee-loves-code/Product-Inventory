package com.example.productinventory.controller;

import com.example.productinventory.entity.Product;
import com.example.productinventory.payload.request.ProductRequest;
import com.example.productinventory.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1")
public class ProductController {

private  final ProductService productService;

@PostMapping(value = "/products")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductRequest productRequest){
    return  new ResponseEntity<>(productService.saveProduct(productRequest), HttpStatus.CREATED);
}

@PutMapping(value = "/update-product/{product-id}")
    public  ResponseEntity<?> updateProduct(ProductRequest productRequest, @PathVariable Long productId){
    return  new ResponseEntity<>(productService.updateProduct(productId, productRequest), HttpStatus.OK);
}

@GetMapping(value = "/get-product/{product-id}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId){
    return  new ResponseEntity<>(productService.getProduct(productId), HttpStatus.OK);
}


}
