package com.papajohns.pj_app.Models.DTO.CreateDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrganizarionCreateDTO {
    private String legalName;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    private int number;
    private String country;
}
