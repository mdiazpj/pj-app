package com.papajohns.pj_app.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ProductOption {
    @Id
    private Long id;
    private String imageUrl;
    private String title;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    private double price;
}