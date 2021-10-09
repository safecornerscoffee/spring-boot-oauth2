package com.safecornerscoffeee.oauth2.member.repository;

import com.safecornerscoffeee.oauth2.member.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findByName(String name);
}
