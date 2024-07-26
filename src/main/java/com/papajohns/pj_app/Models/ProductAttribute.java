package com.papajohns.pj_app.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean withPrice;
    private int min;
    private int max;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    @JsonProperty("isRequired")
    private boolean isRequired;
    private boolean selectSameOption;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "attribute_id")
    private List<ProductOption> options;
}