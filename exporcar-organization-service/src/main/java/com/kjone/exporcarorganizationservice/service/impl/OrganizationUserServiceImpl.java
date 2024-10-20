package com.kjone.exporcarorganizationservice.service.impl;



import com.kjone.exporcarorganizationservice.domain.organization_user.LoginRequest;
import com.kjone.exporcarorganizationservice.domain.organization_user.Organization_User;
import com.kjone.exporcarorganizationservice.domain.request.SignRequest;
import com.kjone.exporcarorganizationservice.domain.response.SignResponse;
import com.kjone.exporcarorganizationservice.domain.role.Authority;
import com.kjone.exporcarorganizationservice.repository.OrganizationRepository;
import com.kjone.exporcarorganizationservice.repository.OrganizationUserRepository;
import com.kjone.exporcarorganizationservice.security.cookie.CookieProvider;
import com.kjone.exporcarorganizationservice.service.OrganizationUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationUserServiceImpl implements OrganizationUserService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationUserRepository organizationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CookieProvider cookieProvider;

    @Override
    @Transactional
    public boolean Organization_signUp(SignRequest signRequest) throws Exception{
        try {

            // 이메일 중복 확인
            Optional<Organization_User> existingUser = organizationUserRepository.findByEmail(signRequest.getEmail());
            if (existingUser.isPresent()) {
                throw new Exception("이미 사용 중인 이메일입니다.");
            }
            Organization_User organization_user = Organization_User.builder()
                    .email(signRequest.getEmail())
                    .password(passwordEncoder.encode(signRequest.getPassword()))
                    .name(signRequest.getName())
                    .age(signRequest.getAge())
                    .phone(signRequest.getPhone())
                    .image(signRequest.getImage())
                    .roles(Collections.singleton(Authority.OWNER)) // roles 필드를 설정합니다.
                    .build();
            organizationUserRepository.save(organization_user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("이미 사용 중인 이메일 입니다.");
        }
        return true;
    }

    // 로그인 메서드
    @Override
    public SignResponse Organization_signIn(LoginRequest loginRequest) throws Exception {
        Organization_User organization_user = organizationUserRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (passwordEncoder.matches(loginRequest.getPassword(), organization_user.getPassword())) {
            return new SignResponse(organization_user);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }


    @Override
    public void logout(HttpServletResponse response) {
        Cookie access = cookieProvider.of(cookieProvider.removeAccessTokenCookie());
        Cookie refresh = cookieProvider.of(cookieProvider.removeRefreshTokenCookie());

        response.addCookie(access);
        response.addCookie(refresh);
    }

    @Override
    public Organization_User findByOrgEmail(String email) {
        return organizationUserRepository.findByEmail(email).orElse(null); // 사용자 없을 경우 null 반환
    }

    @Override
    @Transactional
    public void deleteOrgByEmail(String email) throws Exception {
        Organization_User organization_user = organizationUserRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("사용자를 찾을 수 없습니다."));
        organizationUserRepository.delete(organization_user);
    }

    @Override
    public List<Organization_User> getAllUsers() {

        return organizationUserRepository.findAll().stream()
                .map(organization_user -> new Organization_User(
                        organization_user.getId(),
                        organization_user.getEmail(),
                        organization_user.getPassword(),
                        organization_user.getName(),
                        organization_user.getAge(),
                        organization_user.getPhone(),
                        organization_user.getCreateTime(),
                        organization_user.getUpdateTime(),
                        organization_user.getRoles()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Organization_User createProfile(Long id, SignRequest signRequest) throws Exception {
        Organization_User organization_user = organizationUserRepository.findById(id)
                .orElseThrow(() -> new Exception("사용자를 찾을 수 없습니다."));

        organization_user.setName(signRequest.getName());
        organization_user.setAge(signRequest.getAge());
        organization_user.setImage(signRequest.getImage());
        organization_user.setUpdateTime(LocalDateTime.now());

        return organizationUserRepository.save(organization_user);
    }

    @Override
    @Transactional
    public Organization_User updateProfile(Long id, SignRequest signRequest) throws Exception {
        Organization_User organization_user = organizationUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        organization_user.setName(signRequest.getName());
        organization_user.setAge(signRequest.getAge());
        organization_user.setImage(signRequest.getImage());
        organization_user.setUpdateTime(LocalDateTime.now());

        return organizationUserRepository.save(organization_user);
    }



}
