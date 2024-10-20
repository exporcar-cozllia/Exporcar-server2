package com.kjone.exporcarorganizationservice.repository;


import com.kjone.exporcarorganizationservice.domain.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization,Long> {
    //조직 이름으로 검색 하기
    Optional<Organization> findByName(String name);

}
