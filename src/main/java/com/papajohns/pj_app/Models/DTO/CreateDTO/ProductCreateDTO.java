package com.papajohns.pj_app.Models.DTO.CreateDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProductCreateDTO {
    private double price;
    private String title;
    private String description;
    private int orderIndex;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    @JsonProperty("isUpselling")
    private boolean isUpselling;
    private UUID categoryId;
    private List<String> imageUrls;
}