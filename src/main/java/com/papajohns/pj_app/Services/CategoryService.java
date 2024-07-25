package com.papajohns.pj_app.Services;

import com.papajohns.pj_app.Models.Category;
import com.papajohns.pj_app.Models.DTO.CreateDTO.CategoryCreateDTO;
import com.papajohns.pj_app.Models.DTO.CreateDTO.CategoryUpdateDTO;
import com.papajohns.pj_app.Models.DTO.ResponseDTO.CategoryResponseDTO;
import com.papajohns.pj_app.Repositories.CategoryRepository;
import com.papajohns.pj_app.Repositories.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StoreRepository storeRepository;

    public CategoryResponseDTO createCategory(UUID storeId, CategoryCreateDTO categoryRequestDTO) {
        return storeRepository.findById(storeId).map(store -> {
            Category category = Category.builder()
                    .title(categoryRequestDTO.getTitle())
                    .description(categoryRequestDTO.getDescription())
                    .orderIndex(categoryRequestDTO.getOrderIndex())
                    .isEnabled(categoryRequestDTO.isEnabled())
                    .showTotem(categoryRequestDTO.isShowTotem())
                    .imageUrl(categoryRequestDTO.getImageUrl())
                    .store(store)
                    .build();
            Category savedCategory = categoryRepository.save(category);
            return mapToCategoryResponseDTO(savedCategory);
        }).orElse(null);
    }

    public CategoryResponseDTO updateCategory(UUID id, CategoryUpdateDTO categoryUpdateDTO) {
        return categoryRepository.findById(id).map(category -> {
            category.setTitle(categoryUpdateDTO.getTitle());
            category.setDescription(categoryUpdateDTO.getDescription());
            category.setOrderIndex(categoryUpdateDTO.getOrderIndex());
            category.setEnabled(categoryUpdateDTO.isEnabled());
            category.setShowTotem(categoryUpdateDTO.isShowTotem());
            category.setImageUrl(categoryUpdateDTO.getImageUrl());

            if (categoryUpdateDTO.getStoreId() != null) {
                storeRepository.findById(categoryUpdateDTO.getStoreId()).ifPresent(category::setStore);
            }

            Category updatedCategory = categoryRepository.save(category);
            return mapToCategoryResponseDTO(updatedCategory);
        }).orElse(null);
    }

    public List<CategoryResponseDTO> getCategoriesByStoreId(UUID storeId) {
        return categoryRepository.findByStoreId(storeId).stream()
                .map(this::mapToCategoryResponseDTO)
                .collect(Collectors.toList());
    }

    private CategoryResponseDTO mapToCategoryResponseDTO(Category category) {
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        responseDTO.setId(category.getId());
        responseDTO.setTitle(category.getTitle());
        responseDTO.setDescription(category.getDescription());
        responseDTO.setOrderIndex(category.getOrderIndex());
        responseDTO.setEnabled(category.isEnabled());
        responseDTO.setShowTotem(category.isShowTotem());
        responseDTO.setImageUrl(category.getImageUrl());

        CategoryResponseDTO.StoreDTO storeDTO = new CategoryResponseDTO.StoreDTO();
        storeDTO.setId(category.getStore().getId());
        storeDTO.setName(category.getStore().getName());

        CategoryResponseDTO.StoreDTO.OrganizationDTO organizationDTO = new CategoryResponseDTO.StoreDTO.OrganizationDTO();
        organizationDTO.setId(category.getStore().getOrganization().getId());
        organizationDTO.setLegalName(category.getStore().getOrganization().getLegalName());

        storeDTO.setOrganization(organizationDTO);
        responseDTO.setStore(storeDTO);

        return responseDTO;
    }

    public CategoryResponseDTO getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .map(this::mapToCategoryResponseDTO)
                .orElse(null);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.findById(id).ifPresent(categoryRepository::delete);
    }
}