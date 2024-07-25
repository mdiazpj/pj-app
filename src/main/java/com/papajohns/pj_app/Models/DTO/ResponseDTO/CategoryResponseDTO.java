package com.papajohns.pj_app.Models.DTO.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private int orderIndex;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    private boolean showTotem;
    private String imageUrl;
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
