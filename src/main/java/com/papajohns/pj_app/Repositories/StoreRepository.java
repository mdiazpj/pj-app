package com.papajohns.pj_app.Repositories;

import com.papajohns.pj_app.Models.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
    Optional<Store> findByIdAndOrganizationId(UUID id, String organizationId);
    List<Store> findByOrganizationId(String organizationId);
}