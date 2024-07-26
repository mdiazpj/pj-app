package com.papajohns.pj_app.Models.DTO.CreateDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductOptionDTO {
    private Long id;
    private String imageUrl;
    private String title;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    private double price;
}