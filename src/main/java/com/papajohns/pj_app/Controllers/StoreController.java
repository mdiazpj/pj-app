package com.papajohns.pj_app.Controllers;

import com.papajohns.pj_app.Models.DTO.CreateDTO.StoreUpdateDTO;
import com.papajohns.pj_app.Models.Store;
import com.papajohns.pj_app.Services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    // Obtener todas las tiendas
    @GetMapping
    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }

    // Obtener tiendas por organizationId
    @GetMapping("/organization/{organizationId}")
    public List<Store> getStoresByOrganizationId(@PathVariable String organizationId) {
        return storeService.getStoresByOrganizationId(organizationId);
    }

    // Obtener tienda por id de tienda
    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable UUID id) {
        Store store = storeService.getStoreById(id);
        return store != null ? ResponseEntity.ok().body(store) : ResponseEntity.notFound().build();
    }

    // Crear tienda
    @PostMapping("/organization/{organizationId}/save")
    public ResponseEntity<Store> createStore(@PathVariable String organizationId, @RequestBody Store store) {
        Store createdStore = storeService.createStore(organizationId, store);
        return createdStore != null ? ResponseEntity.ok().body(createdStore) : ResponseEntity.notFound().build();
    }

    // Actualizar tienda con id y organizationId en el JSON
    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable UUID id, @RequestBody StoreUpdateDTO storeDetails) {
        Store updatedStore = storeService.updateStore(id, storeDetails);
        return updatedStore != null ? ResponseEntity.ok().body(updatedStore) : ResponseEntity.notFound().build();
    }

    // Borrar tienda por id de tienda
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable UUID id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }
}