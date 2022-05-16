package com.example.flipkart.core;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, I> {

    T save(T entity);
    Optional<T> findById(I id);
    List<T> findAll();
    void deleteById(I id);
}
