package com.kjone.kjoneorganizationservice.service;

import com.kjone.kjoneorganizationservice.domain.organization_user.LoginRequest;
import com.kjone.kjoneorganizationservice.domain.organization_user.Organization_User;
import com.kjone.kjoneorganizationservice.domain.request.SignRequest;
import com.kjone.kjoneorganizationservice.domain.response.SignResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface OrganizationUserService {
    public boolean Organization_signUp(SignRequest signRequest) throws Exception;
    public SignResponse Organization_signIn(LoginRequest loginRequest) throws Exception;
    public void logout(HttpServletResponse response);
    public Organization_User findByOrgEmail(String email);
    public void deleteOrgByEmail(String email) throws Exception; // 사용자 삭제 메서드 정의
    public List<Organization_User> getAllUsers();
    Organization_User createProfile(Long id, SignRequest signRequest) throws Exception;
    Organization_User updateProfile(Long id, SignRequest signRequest) throws Exception;
}
