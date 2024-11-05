package com.akkitech.ecom_backend.controller;

import com.akkitech.ecom_backend.model.Product;
import com.akkitech.ecom_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/product")
    public ResponseEntity<String> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) throws IOException {
        return service.addProduct(product, imageFile);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return service.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id){
        return service.getProductById(id);
    }

    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        return service.getImageByProductId(productId);
    }

    @PutMapping("product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) throws IOException {
        return service.updateProduct(id, product, imageFile);
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        return service.deleteProduct(id);
    }

    @GetMapping("products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        return service.searchProduct(keyword);
    }
}
