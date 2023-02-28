package com.example.productinventory.serviceImpl;

import com.example.productinventory.exception.ResourceNotFoundException;
import com.example.productinventory.entity.Product;
import com.example.productinventory.payload.request.ProductRequest;

import com.example.productinventory.payload.response.ProductResponse;
import com.example.productinventory.repository.ProductRepository;
import com.example.productinventory.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Override
    public ProductResponse saveProduct(ProductRequest productRequest) {
        Optional<Product> optionalProduct = productRepository.findByProductNameOrManufacturer(productRequest.getProductName(), productRequest.getManufacturer());
        Product product;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
            modelMapper.map(productRequest, product);
        } else {
            product = modelMapper.map(productRequest, Product.class);
        }
            productRepository.save(product);
            return modelMapper.map(product, ProductResponse.class);
    }


    @Override
    public ProductResponse updateProduct(long productId, ProductRequest productRequest) {
        Product product = productRepository.findProductById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if(!productRequest.getProductName().isBlank()) {
            product.setProductName(productRequest.getProductName());
        }
        if(!productRequest.getManufacturer().isBlank()) {
            product.setManufacturer(productRequest.getManufacturer());
        }
        if(productRequest.getPrice() != null && !productRequest.getPrice().equals(BigDecimal.ZERO)) {
            product.setPrice(productRequest.getPrice());
        }
        if(productRequest.getQuantityInStore() != 0) {
            product.setQuantityInStore(productRequest.getQuantityInStore());
        }
        if(productRequest.getStatus() != null) {
            product.setStatus(productRequest.getStatus());
        }
        productRepository.save(product);
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        return productResponse;
    }

    @Override
    public ProductResponse getProduct(long productId) throws ResourceNotFoundException{
       Product product = productRepository.findProductById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with id = " + productId + " not found"));
        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    public Page<ProductResponse> getProductByName(String name, int pageNumber, int pageSize) {
        Pageable pageable = modelMapper.map(PageRequest.of(pageNumber, pageSize), Pageable.class);
        Page<Product> products = productRepository.findByProductNameContainingIgnoreCase(name, pageable);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found with the name " + name);
        }
        return modelMapper.map(products, new TypeToken<Page<ProductResponse>>() {}.getType());
    }

    @Override
    public void deleteProduct(long productId) {
        try {
            productRepository.deleteById(productId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Product not found with id " + productId);
        }
    }

    @Override
    public List<ProductResponse> getProductsOver100() throws ResourceNotFoundException {
        List<Product> products = productRepository.findByPriceGreaterThanAndQuantityInStoreEquals(BigDecimal.valueOf(100), 0);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found with price > 100 and quantity in store = 0");
        }

        ModelMapper modelMapper = new ModelMapper();
        List<ProductResponse> productResponses = products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());

        return productResponses;
    }
}
