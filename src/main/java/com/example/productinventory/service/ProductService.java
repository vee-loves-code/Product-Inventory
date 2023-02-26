package com.example.productinventory.service;

import com.example.productinventory.entity.Product;
import com.example.productinventory.payload.request.ProductRequest;
import com.example.productinventory.payload.response.ApiResponse;
import com.example.productinventory.payload.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse saveProduct(ProductRequest productRequest);
    ProductResponse updateProduct(long productId, ProductRequest productRequest);
    ProductResponse getProduct(long productId);
    Page<ProductResponse> getProductByName( String name, int pageNumber, int pageSize);
    void deleteProduct(long productId);
    List<ProductResponse>getProductsOver100();


}
