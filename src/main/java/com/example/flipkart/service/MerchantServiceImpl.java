package com.example.flipkart.service;

import org.springframework.stereotype.Service;

import com.example.flipkart.core.BaseServiceImpl;
import com.example.flipkart.entity.Merchant;
import com.example.flipkart.repository.MerchantRepository;

@Service
public class MerchantServiceImpl extends BaseServiceImpl<Merchant, Long> implements
    MerchantService {

    public MerchantServiceImpl(MerchantRepository repository) {
        super(repository);
    }
}
