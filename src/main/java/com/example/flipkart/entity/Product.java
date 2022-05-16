package com.example.flipkart.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product extends AbstractPersistable<Long> {

    private String name;
    private Float price;

    @ManyToOne(optional = false)
    private Merchant merchant;

}
