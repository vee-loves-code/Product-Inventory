package com.example.productinventory.controller;

import com.example.productinventory.entity.Product;
import com.example.productinventory.exception.ResourceNotFoundException;
import com.example.productinventory.payload.request.ProductRequest;
import com.example.productinventory.payload.response.ProductResponse;
import com.example.productinventory.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/products")
public class ProductController {

private  final ProductService productService;

@PostMapping
    public ResponseEntity<ProductResponse> saveProduct(@RequestBody ProductRequest productRequest){
    ProductResponse productResponse = productService.saveProduct(productRequest);
    return ResponseEntity.ok(productResponse);
}

@PutMapping(value = "/{id}")
public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") long productId, @RequestBody ProductRequest productRequest) {
    ProductResponse productResponse = productService.updateProduct(productId, productRequest);
    return ResponseEntity.ok(productResponse);
}

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable long productId) {
        try {
            ProductResponse productResponse = productService.getProduct(productId);
            return ResponseEntity.ok().body(productResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/product-name/{name}")
    public ResponseEntity<Page<ProductResponse>> getProductsByName(
            @PathVariable String name,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {

        Page<ProductResponse> products = productService.getProductByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(products);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/over100")
    public ResponseEntity<List<ProductResponse>> getProductsOver100() {
        try {
            List<ProductResponse> productResponses = productService.getProductsOver100();
            return ResponseEntity.ok(productResponses);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
