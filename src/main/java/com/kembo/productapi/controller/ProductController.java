package com.kembo.productapi.controller;

import com.kembo.productapi.dto.ProductRequest;
import com.kembo.productapi.dto.ProductResponse;
import com.kembo.productapi.model.Product;
import com.kembo.productapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return new ResponseEntity<>(productService.getListProduct(), HttpStatus.OK);
    }

    @GetMapping("/single-product/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productRequest.id()).toUri();
        return ResponseEntity.created(location).body(productService.createProduct(productRequest));
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") long id, @RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.updateProduct(id, productRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
