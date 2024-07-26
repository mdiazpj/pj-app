package com.papajohns.pj_app.Models.DTO.CreateDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductAttributeDTO {
    private long id;
    private String title;
    private boolean withPrice;
    private int min;
    private int max;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    @JsonProperty("isRequired")
    private boolean isRequired;
    private List<ProductOptionDTO> options;
    private boolean selectSameOption;
}
