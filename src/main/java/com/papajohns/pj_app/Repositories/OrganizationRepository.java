package com.papajohns.pj_app.Repositories;

import com.papajohns.pj_app.Models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {
}