package com.papajohns.pj_app.Services;

import com.papajohns.pj_app.Models.DTO.CreateDTO.OrganizarionCreateDTO;
import com.papajohns.pj_app.Models.Organization;
import com.papajohns.pj_app.Repositories.OrganizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    public Optional<Organization> findById(String id) {
        return organizationRepository.findById(id);
    }

    public Organization save(OrganizarionCreateDTO organizationCreateDTO) {
        Organization organization = new Organization();
        organization.setId(UUID.randomUUID().toString());
        organization.setLegalName(organizationCreateDTO.getLegalName());
        organization.setEnabled(organizationCreateDTO.isEnabled());
        organization.setNumber(organizationCreateDTO.getNumber());
        organization.setCountry(organizationCreateDTO.getCountry());

        return organizationRepository.save(organization);
    }

    public Organization update(String id, OrganizarionCreateDTO organizationCreateDTO) {
        Optional<Organization> existingOrganization = organizationRepository.findById(id);
        if (existingOrganization.isEmpty()) {
            throw new RuntimeException("Organization not found with id: " + id);
        }
        Organization organization = existingOrganization.get();
        organization.setLegalName(organizationCreateDTO.getLegalName());
        organization.setEnabled(organizationCreateDTO.isEnabled());
        organization.setNumber(organizationCreateDTO.getNumber());
        organization.setCountry(organizationCreateDTO.getCountry());

        return organizationRepository.save(organization);
    }
    public void deleteById(String id) {
        organizationRepository.deleteById(id);
    }
}