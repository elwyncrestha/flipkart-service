package com.example.flipkart.service;

import java.util.List;

import com.example.flipkart.core.BaseService;
import com.example.flipkart.entity.Product;

public interface ProductService extends BaseService<Product, Long> {

    List<Product> findAllByMerchantId(Long id);
}
