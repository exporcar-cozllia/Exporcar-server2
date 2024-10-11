package com.kjone.testservice.service.impl;


import com.kjone.testservice.domain.organization.Organization;
import com.kjone.testservice.domain.organization_user.Organization_User;
import com.kjone.testservice.exception.GlobalException;
import com.kjone.testservice.repository.OrganizationRepository;
import com.kjone.testservice.repository.OrganizationUserRepository;
import com.kjone.testservice.security.jwt.JwtProvider;
import com.kjone.testservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationUserRepository organizationUserRepository;
    private final JwtProvider jwtProvider;

    // 조직 생성 관리
//    @Override
//    @Transactional(rollbackFor = {GlobalException.class})
//    public void createOrganization(Organization organization, ) {
//        // 현재 인증된 사용자 정보 가져오기
//        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        // 인증된 사용자 이메일로 조직 사용자 확인
//        Organization_User user = organizationUserRepository.findByEmail(authenticatedUserEmail)
//                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
//
//        // 사용자 권한 확인
//        verifyUserPermissions(user);
//
//        // 조직 이름 유효성 검사
//        if (organization.getName().length() < 3) {
//            throw new GlobalException(HttpStatus.BAD_REQUEST, "조직명은 2자 이상이어야 합니다.");
//        } else if (organization.getName().length() > 50) {
//            throw new GlobalException(HttpStatus.BAD_REQUEST, "조직명은 50자를 넘길 수 없습니다.");
//        }
//
//        // 조직 생성
//        Organization newOrganization = Organization.builder()
//                .name(organization.getName()) // 조직 이름 설정
//                .owner_id(user) // 조직자 설정
//                .build();
//        organizationRepository.save(newOrganization);
//
//        // 조직 생성 성공 메시지 로그 또는 처리
//        System.out.println("조직이 성공적으로 생성되었습니다.");
//    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void createOrganization(Organization organization, String sender) throws Exception{

        // 조직 사용자 확인
        Organization_User user = organizationUserRepository.findByEmail(sender)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 사용자 권한 확인 (Owner인지 여부)
        verifyUserPermissions(user);

        // 조직 이름 유효성 검사
        if (organization.getName().length() < 3) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "조직명은 3자 이상이어야 합니다.");
        } else if (organization.getName().length() > 50) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "조직명은 50자를 넘길 수 없습니다.");
        }

        // 조직 생성
        Organization newOrganization = Organization.builder()
                .name(organization.getName())
                .owner_id(user)  // 조직 소유자 설정
                .build();
        organizationRepository.save(newOrganization);

        // 조직 생성 성공 메시지 로그
        System.out.println("조직이 성공적으로 생성되었습니다.");
    }


    // 사용자 권한 확인 메서드
    private void verifyUserPermissions(Organization_User user) {
        if (!user.isOwner()) {
            throw new GlobalException(HttpStatus.FORBIDDEN, "조직 생성 권한이 없습니다.");
        }
    }

    // 조직 이름으로 조직 정보 가져오기
    @Override
    public Organization getOrganizationName(String name, String sender) throws Exception {
        // 조직 사용자 확인
        Organization_User organizationUser = organizationUserRepository.findByEmail(sender)
                .orElseThrow(() -> new RuntimeException("조직자를 찾을 수 없습니다."));

        // 사용자 권한 확인
        verifyUserPermissions(organizationUser);

        // 조직 이름으로 조직 검색
        return organizationRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("조직을 찾을 수 없습니다."));

//        return (Organization) organizationRepository.findByName(name).stream()
//                .map(organization -> new Organization(
//                        organization.getId(),
//                        organization.getName(),
//                        organization.getOwner_id(),
//                        organization.getCreateTime(),
//                        organization.getUpdateTime()
//                ))
//                .collect(Collectors.toList());
    }



}

