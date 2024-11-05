package com.akkitech.ecom_backend.service;

import com.akkitech.ecom_backend.model.Product;
import com.akkitech.ecom_backend.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;


    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Product> getProductById(int id) {
        try {
            return new ResponseEntity<>(repo.findById(id).orElse(new Product()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addProduct(Product product, MultipartFile imageFile) throws IOException {
        try {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData(imageFile.getBytes());
            repo.save(product);
            return new ResponseEntity<>("New Product Added", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<byte[]> getImageByProductId(int productId) {
        try {
            Product product = getProductById(productId).getBody();
            if (product != null) {
                byte[] imageData = product.getImageData();
                return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageData);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<String> deleteProduct(int id) {
        try {
            Product product = getProductById(id).getBody();
            if(product!=null){
                repo.deleteById(id);
                return ResponseEntity.ok().body("Product deleted");
            }
            else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity<String> updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        try {
            Product product1 = getProductById(id).getBody();
            if(product1!=null){
                product.setImageName(imageFile.getOriginalFilename());
                product.setImageType(imageFile.getContentType());
                product.setImageData(imageFile.getBytes());
                repo.save(product);
                return ResponseEntity.ok().body("Product updated successfully");
            }
            else{
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Update failed!");
        }
    }

    public ResponseEntity<List<Product>> searchProduct(String keyword) {
        try {
            System.out.println("Searching for: "+keyword);
            List<Product> products = repo.getSearchedProducts(keyword);
            return ResponseEntity.ok().body(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
