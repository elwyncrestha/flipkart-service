package com.example.flipkart.core;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public class BaseServiceImpl<T, I> implements BaseService<T, I> {

    private final JpaRepository<T, I> repository;

    public BaseServiceImpl(JpaRepository<T, I> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<T> findById(I id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(I id) {
        repository.deleteById(id);
    }
}
