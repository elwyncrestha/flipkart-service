package com.example.flipkart.core;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.flipkart.dto.ResponseDto;

@Slf4j
public class BaseController<T, I> {

    private final BaseService<T, I> service;

    public BaseController(BaseService<T, I> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> save(@RequestBody T entity) {
        try {
            return ok(service.save(entity));
        } catch (Exception e) {
            log.error("Error saving {}: {}", entity.getClass().getSimpleName(), e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(@PathVariable I id) {
        Optional<T> opt = service.findById(id);
        return opt.map(this::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<ResponseDto> findAll() {
        return ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable I id) {
        Optional<T> opt = service.findById(id);
        if (opt.isPresent()) {
            service.deleteById(id);
            return ok(String.format("Deleted merchant with id: %s", id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    protected ResponseEntity<ResponseDto> ok(Object body) {
        return ResponseEntity.ok(ResponseDto.builder()
            .status(HttpStatus.OK.value())
            .body(body).build()
        );
    }
}
