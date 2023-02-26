package com.example.productinventory.repository;

import com.example.productinventory.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductById(Long id);
    Optional<Product> findByProductNameOrManufacturer(String productName, String manufacturer);
    Page<Product> findByProductNameContainingIgnoreCase(String name, Pageable pageable);

    List<Product> findByPriceGreaterThanAndQuantityInStoreEquals(BigDecimal price, int quantityInStore);
}
