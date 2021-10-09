package com.safecornerscoffeee.oauth2.member.service;

import com.safecornerscoffeee.oauth2.member.domain.Member;
import com.safecornerscoffeee.oauth2.member.domain.Role;
import com.safecornerscoffeee.oauth2.member.dto.MemberDto;
import com.safecornerscoffeee.oauth2.member.exception.RoleNotFoundException;
import com.safecornerscoffeee.oauth2.member.repository.MemberRepository;
import com.safecornerscoffeee.oauth2.member.repository.RoleRepository;
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
