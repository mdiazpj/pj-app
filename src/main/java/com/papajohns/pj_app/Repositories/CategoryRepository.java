package com.papajohns.pj_app.Repositories;

import com.papajohns.pj_app.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByStoreId(UUID storeId);
}