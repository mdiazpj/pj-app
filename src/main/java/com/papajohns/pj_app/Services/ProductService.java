package com.papajohns.pj_app.Services;

import com.papajohns.pj_app.Models.DTO.CreateDTO.ProductCreateDTO;
import com.papajohns.pj_app.Models.DTO.ResponseDTO.ProductResponseDTO;
import com.papajohns.pj_app.Models.Product;
import com.papajohns.pj_app.Repositories.CategoryRepository;
import com.papajohns.pj_app.Repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductResponseDTO createProduct(ProductCreateDTO productRequestDTO) {
        return categoryRepository.findById(productRequestDTO.getCategoryId()).map(category -> {
            Product product = Product.builder()
                    .price(productRequestDTO.getPrice())
                    .title(productRequestDTO.getTitle())
                    .description(productRequestDTO.getDescription())
                    .orderIndex(productRequestDTO.getOrderIndex())
                    .isEnabled(productRequestDTO.isEnabled())
                    .isUpselling(productRequestDTO.isUpselling())
                    .imageUrls(productRequestDTO.getImageUrls())
                    .category(category)
                    .build();
            Product savedProduct = productRepository.save(product);
            return mapToProductResponseDTO(savedProduct);
        }).orElse(null);
    }

    public ProductResponseDTO updateProduct(UUID id, ProductCreateDTO productRequestDTO) {
        return productRepository.findById(id).map(product -> {
            product.setPrice(productRequestDTO.getPrice());
            product.setTitle(productRequestDTO.getTitle());
            product.setDescription(productRequestDTO.getDescription());
            product.setOrderIndex(productRequestDTO.getOrderIndex());
            product.setEnabled(productRequestDTO.isEnabled());
            product.setUpselling(productRequestDTO.isUpselling());
            product.setImageUrls(productRequestDTO.getImageUrls());

            if (productRequestDTO.getCategoryId() != null) {
                categoryRepository.findById(productRequestDTO.getCategoryId()).ifPresent(product::setCategory);
            }

            Product updatedProduct = productRepository.save(product);
            return mapToProductResponseDTO(updatedProduct);
        }).orElse(null);
    }

    public List<ProductResponseDTO> getProductsByCategoryId(UUID categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::mapToProductResponseDTO)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO getProductById(UUID id) {
        return productRepository.findById(id)
                .map(this::mapToProductResponseDTO)
                .orElse(null);
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToProductResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteProduct(UUID id) {
        productRepository.findById(id).ifPresent(productRepository::delete);
    }

    private ProductResponseDTO mapToProductResponseDTO(Product product) {
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setTitle(product.getTitle());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setOrderIndex(product.getOrderIndex());
        responseDTO.setEnabled(product.isEnabled());
        responseDTO.setUpselling(product.isUpselling());
        responseDTO.setImageUrls(product.getImageUrls());

        ProductResponseDTO.CategoryDTO categoryDTO = new ProductResponseDTO.CategoryDTO();
        categoryDTO.setId(product.getCategory().getId());
        categoryDTO.setTitle(product.getCategory().getTitle());

        ProductResponseDTO.CategoryDTO.StoreDTO storeDTO = new ProductResponseDTO.CategoryDTO.StoreDTO();
        storeDTO.setId(product.getCategory().getStore().getId());
        storeDTO.setName(product.getCategory().getStore().getName());

        ProductResponseDTO.CategoryDTO.StoreDTO.OrganizationDTO organizationDTO = new ProductResponseDTO.CategoryDTO.StoreDTO.OrganizationDTO();
        organizationDTO.setId(product.getCategory().getStore().getOrganization().getId());
        organizationDTO.setLegalName(product.getCategory().getStore().getOrganization().getLegalName());


        storeDTO.setOrganization(organizationDTO);
        categoryDTO.setStore(storeDTO);
        responseDTO.setCategory(categoryDTO);

        return responseDTO;
    }
}