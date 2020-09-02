package com.javaguru.shoppingservice.service;

import com.javaguru.shoppingservice.domain.ProductEntity;
import com.javaguru.shoppingservice.dto.ProductDto;
import com.javaguru.shoppingservice.repository.ProductRepository;
import com.javaguru.shoppingservice.validation.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


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

    public void deleteProductById(String id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException("Product not found, id = " + id);
        }
        repository.deleteById(id);
    }

    public List<ProductDto> findAllProducts() {
        return repository.findAll()
                .stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription()))
                .collect(Collectors.toList());
    }

    public void update(ProductDto productDto, String id) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found id : " + id));
        entity.setName(productDto.getName());
        entity.setDescription(productDto.getDescription());
        repository.save(entity);
    }
}