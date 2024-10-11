package com.kjone.testservice.service;

import com.kjone.testservice.domain.organization.Organization;

public interface OrganizationService {
    // organization 생성/ 관리

    public void createOrganization(Organization organization, String sender) throws Exception;

//    public void createOrganization(Organization organization, Authentication authentication) throws Exception;

    public Organization getOrganizationName(String name, String sender) throws Exception;



}
