package com.kjone.kjoneorganizationservice.security.service.impl;


import com.kjone.kjoneorganizationservice.domain.organization_user.OrgForSecurity;
import com.kjone.kjoneorganizationservice.domain.organization_user.Organization_User;
import com.kjone.kjoneorganizationservice.repository.OrganizationUserRepository;
import com.kjone.kjoneorganizationservice.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements CustomUserDetailService {

    private final OrganizationUserRepository organizationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Organization_User organization_user = organizationUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));
        return OrgForSecurity.builder()
                .organization_user(organization_user)
                .build();
    }

}