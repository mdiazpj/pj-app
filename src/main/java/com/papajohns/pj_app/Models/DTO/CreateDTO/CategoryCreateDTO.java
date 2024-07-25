package com.papajohns.pj_app.Models.DTO.CreateDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryCreateDTO {
    private String title;
    private String description;
    private int orderIndex;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    private boolean showTotem;
    private String imageUrl;
}