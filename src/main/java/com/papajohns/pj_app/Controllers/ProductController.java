package com.papajohns.pj_app.Controllers;

import com.papajohns.pj_app.Models.DTO.CreateDTO.ProductCreateDTO;
import com.papajohns.pj_app.Models.DTO.ResponseDTO.ProductResponseDTO;
import com.papajohns.pj_app.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductCreateDTO productRequestDTO) {
        ProductResponseDTO createdProduct = productService.createProduct(productRequestDTO);
        return createdProduct != null ? ResponseEntity.ok().body(createdProduct) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID id, @RequestBody ProductCreateDTO productRequestDTO) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, productRequestDTO);
        return updatedProduct != null ? ResponseEntity.ok().body(updatedProduct) : ResponseEntity.notFound().build();
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponseDTO> getProductsByCategoryId(@PathVariable UUID categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID id) {
        ProductResponseDTO product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok().body(product) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
