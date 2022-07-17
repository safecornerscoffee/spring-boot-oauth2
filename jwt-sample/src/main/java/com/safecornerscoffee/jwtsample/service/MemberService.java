package com.safecornerscoffee.jwtsample.service;

import com.safecornerscoffee.jwtsample.domain.Member;
import com.safecornerscoffee.jwtsample.domain.Role;
import com.safecornerscoffee.jwtsample.dto.MemberDto;
import com.safecornerscoffee.jwtsample.exception.RoleNotFoundException;
import com.safecornerscoffee.jwtsample.repository.MemberRepository;
import com.safecornerscoffee.jwtsample.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member registerMember(MemberDto dto) {

        Role roleUser = roleRepository.findByName("ROLE_USER").orElseThrow(RoleNotFoundException::new);

        Member member = Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .role(roleUser)
                .build();

        return memberRepository.save(member);
    }
}
