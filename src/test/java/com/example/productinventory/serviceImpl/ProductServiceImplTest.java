package com.example.productinventory.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.productinventory.entity.Product;
import com.example.productinventory.exception.ResourceNotFoundException;
import com.example.productinventory.payload.request.ProductRequest;
import com.example.productinventory.payload.response.ProductResponse;
import com.example.productinventory.repository.ProductRepository;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    /**
     * Method under test: {@link ProductServiceImpl#saveProduct(ProductRequest)}
     */
    @Test
    void testSaveProduct() {
        when(productRepository.save((Product) any())).thenReturn(new Product());
        when(productRepository.findByProductNameOrManufacturer((String) any(), (String) any()))
                .thenReturn(Optional.of(new Product()));
        ProductResponse productResponse = new ProductResponse(123L, "Test product", 10.0d);

        when(modelMapper.map((Object) any(), (Class<ProductResponse>) any())).thenReturn(productResponse);
        doNothing().when(modelMapper).map((Object) any(), (Object) any());
        assertSame(productResponse, productServiceImpl.saveProduct(new ProductRequest("Test product", 10.0d)));
        verify(productRepository).save((Product) any());
        verify(productRepository).findByProductNameOrManufacturer((String) any(), (String) any());
        verify(modelMapper).map((Object) any(), (Class<ProductResponse>) any());
        verify(modelMapper).map((Object) any(), (Object) any());
    }
    @Test
    void testUpdateProduct() {
        when(productRepository.findProductById((Long) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> productServiceImpl.updateProduct(123L, new ProductRequest("Test product", 10.0d)));
        verify(productRepository).findProductById((Long) any());
    }


    @Test
    void testGetProduct() throws ResourceNotFoundException {
        when(productRepository.findProductById((Long) any())).thenReturn(Optional.of(new Product()));
        when(modelMapper.map((Object) any(), (Class<ProductResponse>) any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> productServiceImpl.getProduct(123L));
        verify(productRepository).findProductById((Long) any());
        verify(modelMapper).map((Object) any(), (Class<ProductResponse>) any());
    }

    @Test
    void testGetProductByName() {
        when(productRepository.findByProductNameContainingIgnoreCase((String) any(), (Pageable) any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        when(modelMapper.map((Object) any(), (Class<Pageable>) any())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> productServiceImpl.getProductByName("Name", 10, 3));
        verify(productRepository).findByProductNameContainingIgnoreCase((String) any(), (Pageable) any());
        verify(modelMapper).map((Object) any(), (Class<Pageable>) any());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById((Long) any());
        productServiceImpl.deleteProduct(123L);
        verify(productRepository).deleteById((Long) any());
    }

    @Test
    void testGetProductsOver100() throws ResourceNotFoundException {
        when(productRepository.findByPriceGreaterThanAndQuantityInStoreEquals((BigDecimal) any(), anyInt()))
                .thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, () -> productServiceImpl.getProductsOver100());
        verify(productRepository).findByPriceGreaterThanAndQuantityInStoreEquals((BigDecimal) any(), anyInt());
    }

}

