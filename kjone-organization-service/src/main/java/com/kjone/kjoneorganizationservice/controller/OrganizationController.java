package com.kjone.kjoneorganizationservice.controller;

import com.kjone.kjoneorganizationservice.domain.organization.Organization;
import com.kjone.kjoneorganizationservice.expection.GlobalException;
import com.kjone.kjoneorganizationservice.service.OrganizationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;


    // 조직 생성 엔드포인트
//    @PostMapping("/create")
//    public ResponseEntity<String> createOrganization(@RequestBody Organization organization, Authentication authentication) {
//        // 인증된 사용자 확인
//        if (authentication != null) {
//            System.out.println("인증된 사용자: " + authentication.getName());
//            try {
//                // 조직 생성 서비스 호출
//                organizationService.createOrganization(organization, authentication);
//                return ResponseEntity.status(HttpStatus.CREATED).body("조직이 성공적으로 생성되었습니다.");
//            } catch (GlobalException e) {
//                // 사용자 정의 예외 처리
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//            } catch (Exception e) {
//                // 일반 예외 처리
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("조직 생성에 실패했습니다.");
//            }
//        }
//        return new ResponseEntity<>("인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED);
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrganization(@RequestBody Organization organization, @RequestParam String sender) throws Exception {
        // 인증된 사용자만 조직 생성 허용
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            System.out.println("Created User: " + SecurityContextHolder.getContext().getAuthentication().getName());
            organizationService.createOrganization(organization, sender);
            return ResponseEntity.status(HttpStatus.CREATED).body("조직이 성공적으로 생성되었습니다.");
        }
        return new ResponseEntity<>("인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED); // 인증되지 않은 사용자 접근 시 403 반환
    }


    // 조직 이름으로 조직 조회 엔드포인트
    @GetMapping("/{name}")
    public ResponseEntity<Organization> getOrganization(@PathVariable String name, @RequestParam String sender) {
        try {
            Organization organization = organizationService.getOrganizationName(name, sender);
            return ResponseEntity.ok(organization);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
