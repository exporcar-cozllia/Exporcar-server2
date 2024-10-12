package com.kjone.exporcarorganizationservice.service;


import com.kjone.exporcarorganizationservice.domain.organization.Organization;

public interface OrganizationService {
    // organization 생성/ 관리

    public void createOrganization(Organization organization, String sender) throws Exception;
    public Organization getOrganizationName(String name, String sender) throws Exception;

}
