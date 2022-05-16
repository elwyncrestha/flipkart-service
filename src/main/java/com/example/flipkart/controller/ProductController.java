package com.example.flipkart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flipkart.core.BaseController;
import com.example.flipkart.dto.ResponseDto;
import com.example.flipkart.entity.Product;
import com.example.flipkart.service.ProductService;

@RestController
@RequestMapping(ProductController.API)
public class ProductController extends BaseController<Product, Long> {

    public static final String API = "/product";

    private final ProductService service;

    public ProductController(ProductService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<ResponseDto> findProductsByMerchantId(@PathVariable Long merchantId) {
        return ok(service.findAllByMerchantId(merchantId));
    }
}
