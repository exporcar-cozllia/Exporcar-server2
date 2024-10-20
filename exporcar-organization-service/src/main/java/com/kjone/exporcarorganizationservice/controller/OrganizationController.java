package com.kjone.exporcarorganizationservice.controller;



import com.kjone.exporcarorganizationservice.domain.organization.Organization;
import com.kjone.exporcarorganizationservice.repository.OrganizationRepository;
import com.kjone.exporcarorganizationservice.service.OrganizationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


// 조직도 생성/관리
@RestController
@RequestMapping("/v1/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationRepository organizationRepository;

//    @PostMapping("/create")
//    public ResponseEntity<String> createOrganization(
//            @RequestBody Organization organization,
//            @AuthenticationPrincipal UserDetails userDetails) { // 현재 인증된 사용자 정보 가져오기
//        String senderEmail = userDetails.getUsername(); // 사용자 이메일 추출
//        try {
//            organizationService.createOrganization(organization, senderEmail);
//            return ResponseEntity.status(HttpStatus.CREATED).body("조직이 성공적으로 생성되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    // 조직 생성 엔드포인트
    @PostMapping("/create")
    public ResponseEntity<?> createOrganization(@RequestBody Organization organization, @RequestParam String sender) throws Exception {
        // 인증된 사용자만 로그아웃을 허용
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            System.out.println("Create User: " + SecurityContextHolder.getContext().getAuthentication().getName());

            organizationService.createOrganization(organization, sender);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 인증되지 않은 사용자 접근 시 403 반환
    }


    // 조직 이름으로 조직 조회 엔드포인트
    @GetMapping("/get/organization")
    public ResponseEntity<Organization> getOrganization(@RequestParam String name, @RequestParam String sender) throws Exception {
        // 인증된 사용자만 정보 가져오기
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            System.out.println("get User: " + SecurityContextHolder.getContext().getAuthentication().getName());

            organizationService.getOrganizationName(name, sender);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 인증되지 않은 사용자 접근 시 403 반환
    }
}
