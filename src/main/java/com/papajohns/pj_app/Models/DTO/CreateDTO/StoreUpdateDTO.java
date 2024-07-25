package com.papajohns.pj_app.Models.DTO.CreateDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.papajohns.pj_app.Models.Address;
import com.papajohns.pj_app.Models.Contact;
import lombok.Data;

@Data
public class StoreUpdateDTO {
    private String name;
    private Contact contact;
    private Address address;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    private boolean isOpen;
    private String organizationId;
}