package com.papajohns.pj_app.Services;

import com.papajohns.pj_app.Models.DTO.CreateDTO.ProductAttributeDTO;
import com.papajohns.pj_app.Models.DTO.CreateDTO.ProductCreateDTO;
import com.papajohns.pj_app.Models.DTO.CreateDTO.ProductOptionDTO;
import com.papajohns.pj_app.Models.DTO.ProductDTO;
import com.papajohns.pj_app.Models.DTO.ResponseDTO.ProductResponseDTO;
import com.papajohns.pj_app.Models.Product;
import com.papajohns.pj_app.Models.ProductAttribute;
import com.papajohns.pj_app.Models.ProductOption;
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

    public ProductDTO addAttributeToProduct(UUID productId, ProductAttributeDTO attributeDTO) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

        ProductAttribute attribute = new ProductAttribute();
        attribute.setTitle(attributeDTO.getTitle());
        attribute.setWithPrice(attributeDTO.isWithPrice());
        attribute.setMin(attributeDTO.getMin());
        attribute.setMax(attributeDTO.getMax());
        attribute.setEnabled(attributeDTO.isEnabled());
        attribute.setRequired(attributeDTO.isRequired());
        attribute.setSelectSameOption(attributeDTO.isSelectSameOption());

        attribute.setOptions(attributeDTO.getOptions().stream().map(optionDTO -> {
            ProductOption option = new ProductOption();
            option.setId(optionDTO.getId()); // Asignación manual del ID
            option.setTitle(optionDTO.getTitle());
            option.setImageUrl(optionDTO.getImageUrl());
            option.setEnabled(optionDTO.isEnabled());
            option.setPrice(optionDTO.getPrice());
            return option;
        }).collect(Collectors.toList()));

        product.getAttributes().add(attribute);
        product = productRepository.save(product);
        return mapToProductDTO(product);
    }

    public ProductDTO updateAttributeInProduct(UUID productId, Long attributeId, ProductAttributeDTO attributeDTO) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

        ProductAttribute attribute = product.getAttributes().stream().filter(attr -> attr.getId().equals(attributeId))
                .findFirst().orElseThrow(() -> new RuntimeException("Attribute not found with id " + attributeId));

        attribute.setTitle(attributeDTO.getTitle());
        attribute.setWithPrice(attributeDTO.isWithPrice());
        attribute.setMin(attributeDTO.getMin());
        attribute.setMax(attributeDTO.getMax());
        attribute.setEnabled(attributeDTO.isEnabled());
        attribute.setRequired(attributeDTO.isRequired());
        attribute.setSelectSameOption(attributeDTO.isSelectSameOption());

        // Actualizar las opciones existentes
        attribute.getOptions().clear();
        attribute.getOptions().addAll(attributeDTO.getOptions().stream().map(optionDTO -> {
            ProductOption option = new ProductOption();
            option.setId(optionDTO.getId()); // Asignación manual del ID
            option.setTitle(optionDTO.getTitle());
            option.setImageUrl(optionDTO.getImageUrl());
            option.setEnabled(optionDTO.isEnabled());
            option.setPrice(optionDTO.getPrice());
            return option;
        }).collect(Collectors.toList()));

        product = productRepository.save(product);
        return mapToProductDTO(product);
    }

    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setTitle(product.getTitle());
        productDTO.setDescription(product.getDescription());
        productDTO.setOrderIndex(product.getOrderIndex());
        productDTO.setEnabled(product.isEnabled());
        productDTO.setUpselling(product.isUpselling());
        productDTO.setImageUrls(product.getImageUrls());
        productDTO.setCategoryId(product.getCategory().getId());

        productDTO.setAttributes(product.getAttributes().stream().map(attribute -> {
            ProductAttributeDTO attributeDTO = new ProductAttributeDTO();
            attributeDTO.setId(attribute.getId());
            attributeDTO.setTitle(attribute.getTitle());
            attributeDTO.setWithPrice(attribute.isWithPrice());
            attributeDTO.setMin(attribute.getMin());
            attributeDTO.setMax(attribute.getMax());
            attributeDTO.setEnabled(attribute.isEnabled());
            attributeDTO.setRequired(attribute.isRequired());
            attributeDTO.setSelectSameOption(attribute.isSelectSameOption());

            attributeDTO.setOptions(attribute.getOptions().stream().map(option -> {
                ProductOptionDTO optionDTO = new ProductOptionDTO();
                optionDTO.setId(option.getId());
                optionDTO.setTitle(option.getTitle());
                optionDTO.setImageUrl(option.getImageUrl());
                optionDTO.setEnabled(option.isEnabled());
                optionDTO.setPrice(option.getPrice());
                return optionDTO;
            }).collect(Collectors.toList()));

            return attributeDTO;
        }).collect(Collectors.toList()));

        return productDTO;
    }

    public List<ProductAttributeDTO> getAttributesByProductId(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        return product.getAttributes().stream().map(this::mapToProductAttributeDTO).collect(Collectors.toList());
    }

    public ProductAttributeDTO getAttributeById(UUID productId, Long attributeId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        ProductAttribute attribute = product.getAttributes().stream().filter(attr -> attr.getId().equals(attributeId))
                .findFirst().orElseThrow(() -> new RuntimeException("Attribute not found with id " + attributeId));
        return mapToProductAttributeDTO(attribute);
    }

    public void deleteAttributeById(UUID productId, Long attributeId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        ProductAttribute attribute = product.getAttributes().stream().filter(attr -> attr.getId().equals(attributeId))
                .findFirst().orElseThrow(() -> new RuntimeException("Attribute not found with id " + attributeId));
        product.getAttributes().remove(attribute);
        productRepository.save(product);
    }

    private ProductAttributeDTO mapToProductAttributeDTO(ProductAttribute attribute) {
        ProductAttributeDTO attributeDTO = new ProductAttributeDTO();
        attributeDTO.setId(attribute.getId());
        attributeDTO.setTitle(attribute.getTitle());
        attributeDTO.setWithPrice(attribute.isWithPrice());
        attributeDTO.setMin(attribute.getMin());
        attributeDTO.setMax(attribute.getMax());
        attributeDTO.setEnabled(attribute.isEnabled());
        attributeDTO.setRequired(attribute.isRequired());
        attributeDTO.setSelectSameOption(attribute.isSelectSameOption());

        attributeDTO.setOptions(attribute.getOptions().stream().map(option -> {
            ProductOptionDTO optionDTO = new ProductOptionDTO();
            optionDTO.setId(option.getId());
            optionDTO.setTitle(option.getTitle());
            optionDTO.setImageUrl(option.getImageUrl());
            optionDTO.setEnabled(option.isEnabled());
            optionDTO.setPrice(option.getPrice());
            return optionDTO;
        }).collect(Collectors.toList()));

        return attributeDTO;
    }
}