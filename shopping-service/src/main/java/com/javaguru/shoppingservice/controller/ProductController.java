package com.javaguru.shoppingservice.controller;

import com.javaguru.shoppingservice.dto.ProductDto;
import com.javaguru.shoppingservice.service.ProductService;
import com.javaguru.shoppingservice.validation.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
//
//    @GetMapping
//    public List<ProductDto> findAllProducts() {
//        List<ProductDto> productDtos = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            productDtos.add(new ProductDto(
//                    UUID.randomUUID().toString(),
//                    "TEST_NAME" + i,
//                    "TEST_DESCRIPTION"
//            ));
//        }
//        return productDtos;
//    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable String id) {
        System.out.println("Received find by id request: " + id);
        return productService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, UriComponentsBuilder builder) {
        System.out.println("Received create product request: " + productDto);
        ProductDto response = productService.save(productDto);
        return ResponseEntity.created(
                builder.path("/products/{id}")
                        .buildAndExpand(response.getId()).toUri()).build();
    }
//
//    @PutMapping("/{id}")
//    public void updateProduct(@PathVariable String id, @RequestBody ProductDto productDto) {
//        System.out.println("id " + id);
//        System.out.println("update " + productDto);
//    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public void handleNotFound(ProductNotFoundException exception) {
        System.out.println(exception.getMessage());
    }
}
