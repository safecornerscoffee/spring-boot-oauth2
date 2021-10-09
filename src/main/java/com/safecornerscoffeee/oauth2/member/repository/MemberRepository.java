package com.safecornerscoffeee.oauth2.member.repository;

import com.safecornerscoffeee.oauth2.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
}
