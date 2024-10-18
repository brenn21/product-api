package com.kembo.productapi.service;

import com.kembo.productapi.dto.ProductRequest;
import com.kembo.productapi.dto.ProductResponse;
import com.kembo.productapi.exception.ProductException;
import com.kembo.productapi.model.Product;
import com.kembo.productapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getListProduct() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice()))
                .toList();
    }

    public ProductResponse getProductById(long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ProductException();
        }
        return new ProductResponse(productOptional.get().getId(), productOptional.get().getName(), productOptional.get().getPrice());
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        return new ProductResponse(product.getId(), product.getName(), product.getPrice());
    }

    public ProductResponse updateProduct(long id, ProductRequest productRequest) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            optionalProduct.get().setName(productRequest.name());
            optionalProduct.get().setPrice(productRequest.price());
            productRepository.save(optionalProduct.get());
        }
        else {
            throw new ProductException();
        }
        return new ProductResponse(optionalProduct.get().getId(), optionalProduct.get().getName(), optionalProduct.get().getPrice());
    }

    public String deleteProduct(long id) {
        String message;
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            message = String.format("Product with id %s was deleted", id);
        }
        else {
            message = String.format("Product with id %s don't exist in our stock...", id);
        }
        return message;
    }

}
