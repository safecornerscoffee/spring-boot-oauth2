package com.safecornerscoffeee.oauth2.member.repository;

import com.safecornerscoffeee.oauth2.member.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
