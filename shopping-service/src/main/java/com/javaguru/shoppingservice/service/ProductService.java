package com.javaguru.shoppingservice.service;

import com.javaguru.shoppingservice.domain.ProductEntity;
import com.javaguru.shoppingservice.dto.ProductDto;
import com.javaguru.shoppingservice.repository.ProductRepository;
import com.javaguru.shoppingservice.validation.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }


    public ProductDto save(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.randomUUID().toString());
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());

        repository.save(productEntity);

        return new ProductDto(productEntity.getId(), productEntity.getName(), productEntity.getDescription());

    }

    public ProductDto findById(String id) {
        return repository.findById(id)
                .map(entity -> new ProductDto(entity.getId(), entity.getName(), entity.getDescription()))
                .orElseThrow(() -> new ProductNotFoundException("Product not found id : " + id));
    }
}
