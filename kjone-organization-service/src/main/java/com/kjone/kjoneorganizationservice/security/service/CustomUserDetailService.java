package com.kjone.kjoneorganizationservice.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailService {
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

}
