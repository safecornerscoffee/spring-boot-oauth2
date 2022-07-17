package com.safecornerscoffee.jwtsample.repository;

import com.safecornerscoffee.jwtsample.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findByName(String name);
}
