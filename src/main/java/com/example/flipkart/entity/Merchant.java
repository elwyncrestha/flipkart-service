package com.example.flipkart.entity;

import javax.persistence.Entity;

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
public class Merchant extends AbstractPersistable<Long> {

    private String name;
    private String mobileNumber;
    private String emailAddress;
    private String panNumber;

    @Override
    public void setId(Long id) {
        super.setId(id);
    }
}
