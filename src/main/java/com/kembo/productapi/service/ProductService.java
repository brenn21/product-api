package com.kembo.productapi.service;

import com.kembo.productapi.model.Product;
import com.kembo.productapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getListProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new RuntimeException("Product not found...");
        }
        return productOptional;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            optionalProduct.get().setName(product.getName());
            optionalProduct.get().setPrice(product.getPrice());
            return productRepository.save(optionalProduct.get());
        }
        else {
            return optionalProduct.orElseThrow(()->new RuntimeException("Product not found..."));
        }
    }

//    public String deleteProduct(long id) {
//        String message;
//        message = String.format("Product %s was deleted...", id);
//        productRepository.deleteById(id);
//        return message;
//    }

    public String deleteProduct(long id) {
        String message;
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            message = String.format("Produit with id %s was deleted", id);
        }
        else {
            message = String.format("Id %s don't exist...", id);
        }
        return message;
    }

}
