package com.example.flipkart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.flipkart.core.BaseServiceImpl;
import com.example.flipkart.entity.Product;
import com.example.flipkart.repository.ProductRepository;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements
    ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Product> findAllByMerchantId(Long id) {
        return repository.findAllByMerchantId(id);
    }

}
