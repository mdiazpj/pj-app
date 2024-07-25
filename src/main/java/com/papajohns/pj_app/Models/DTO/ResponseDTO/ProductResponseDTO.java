package com.papajohns.pj_app.Models.DTO.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProductResponseDTO {
    private UUID id;
    private double price;
    private String title;
    private String description;
    private int orderIndex;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    @JsonProperty("isUpselling")
    private boolean isUpselling;
    private List<String> imageUrls;
    private CategoryDTO category;

    @Data
    public static class CategoryDTO {
        private UUID id;
        private String title;
        private StoreDTO store;

        @Data
        public static class StoreDTO {
            private UUID id;
            private String name;
            private OrganizationDTO organization;

            @Data
            public static class OrganizationDTO {
                private String id;
                private String legalName;
            }
        }
    }
}