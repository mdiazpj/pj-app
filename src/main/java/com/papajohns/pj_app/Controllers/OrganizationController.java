package com.papajohns.pj_app.Controllers;

import com.papajohns.pj_app.Models.DTO.CreateDTO.OrganizarionCreateDTO;
import com.papajohns.pj_app.Models.Organization;
import com.papajohns.pj_app.Services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Organization> getOrganizationById(@PathVariable String id) {
        return organizationService.findById(id);
    }

    @PostMapping("/save")
    public Organization createOrganization(@RequestBody OrganizarionCreateDTO organizationCreateDTO) {
        return organizationService.save(organizationCreateDTO);
    }

    @PutMapping("/{id}")
    public Organization updateOrganization(@PathVariable String id, @RequestBody OrganizarionCreateDTO organizationCreateDTO) {
        return organizationService.update(id, organizationCreateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrganization(@PathVariable String id) {
        organizationService.deleteById(id);
    }
}
