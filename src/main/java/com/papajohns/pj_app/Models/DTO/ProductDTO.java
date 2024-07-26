package com.papajohns.pj_app.Models.DTO;

import com.papajohns.pj_app.Models.DTO.CreateDTO.ProductAttributeDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProductDTO {
    private UUID id;
    private double price;
    private String title;
    private String description;
    private int orderIndex;
    private boolean isEnabled;
    private boolean isUpselling;
    private List<String> imageUrls;
    private UUID categoryId;
    private List<ProductAttributeDTO> attributes;
}