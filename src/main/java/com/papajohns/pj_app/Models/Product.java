package com.papajohns.pj_app.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    private double price;
    private String title;
    private String description;
    private int orderIndex;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    @JsonProperty("isUpselling")
    private boolean isUpselling;

    @ElementCollection
    private List<String> imageUrls;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}