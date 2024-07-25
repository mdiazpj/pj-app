package com.papajohns.pj_app.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Embedded
    private Contact contact;

    @Embedded
    private Address address;

    @JsonProperty("isEnabled")
    private boolean isEnabled;
    @JsonProperty("isOpen")
    private boolean isOpen;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
}