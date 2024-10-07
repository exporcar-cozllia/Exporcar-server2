package com.kjone.kjoneorganizationservice.service;

import com.kjone.kjoneorganizationservice.domain.organization.Organization;
import org.springframework.security.core.Authentication;

public interface OrganizationService {
    // organization 생성/ 관리

    public void createOrganization(Organization organization, String sender) throws Exception;

//    public void createOrganization(Organization organization, Authentication authentication) throws Exception;

    public Organization getOrganizationName(String name, String sender) throws Exception;

}
