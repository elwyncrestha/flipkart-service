package com.example.flipkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.flipkart.entity.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
