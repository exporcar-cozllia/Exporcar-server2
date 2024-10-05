package com.kjone.kjoneorganizationservice.controller;

import com.kjone.kjoneorganizationservice.domain.organization.Organization;
import com.kjone.kjoneorganizationservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;


    // 조직 생성 엔드포인트
    @PostMapping("/create")
    public ResponseEntity<String> createOrganization(@RequestBody Organization organization, @RequestParam String sender) {
        try {
            organizationService.createOrganization(organization, sender);
            return ResponseEntity.status(HttpStatus.CREATED).body("조직이 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
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
