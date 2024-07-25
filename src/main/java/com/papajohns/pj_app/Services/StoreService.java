package com.papajohns.pj_app.Services;

import com.papajohns.pj_app.Models.DTO.CreateDTO.StoreUpdateDTO;
import com.papajohns.pj_app.Models.Organization;
import com.papajohns.pj_app.Models.Store;
import com.papajohns.pj_app.Repositories.OrganizationRepository;
import com.papajohns.pj_app.Repositories.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public List<Store> getStoresByOrganizationId(String organizationId) {
        return storeRepository.findByOrganizationId(organizationId);
    }

    public Store getStoreById(UUID id) {
        return storeRepository.findById(id).orElse(null);
    }

    public Store createStore(String organizationId, Store store) {
        return organizationRepository.findById(organizationId).map(organization -> {
            store.setOrganization(organization);
            return storeRepository.save(store);
        }).orElse(null);
    }

    public Store updateStore(UUID id, StoreUpdateDTO storeDetails) {
        return storeRepository.findById(id).map(store -> {
            if (storeDetails.getOrganizationId() != null &&
                    organizationRepository.existsById(storeDetails.getOrganizationId())) {
                Organization organization = organizationRepository.findById(storeDetails.getOrganizationId()).orElse(null);
                if (organization != null) {
                    store.setOrganization(organization);
                }
            }
            store.setName(storeDetails.getName());
            store.setContact(storeDetails.getContact());
            store.setAddress(storeDetails.getAddress());
            store.setEnabled(storeDetails.isEnabled());
            store.setOpen(storeDetails.isOpen());
            return storeRepository.save(store);
        }).orElse(null);
    }

    public void deleteStore(UUID id) {
        storeRepository.findById(id).ifPresent(storeRepository::delete);
    }
}