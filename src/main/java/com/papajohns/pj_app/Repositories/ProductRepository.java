package com.papajohns.pj_app.Repositories;

import com.papajohns.pj_app.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategoryId(UUID categoryId);
}