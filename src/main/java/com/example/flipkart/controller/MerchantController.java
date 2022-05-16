package com.example.flipkart.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flipkart.core.BaseController;
import com.example.flipkart.entity.Merchant;
import com.example.flipkart.service.MerchantService;

@RestController
@RequestMapping(MerchantController.API)
public class MerchantController extends BaseController<Merchant, Long> {

    public static final String API = "/merchant";

    public MerchantController(MerchantService service) {
        super(service);
    }
}
