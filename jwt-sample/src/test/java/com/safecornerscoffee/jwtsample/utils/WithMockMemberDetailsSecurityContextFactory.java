package com.safecornerscoffee.jwtsample.utils;

import com.safecornerscoffee.jwtsample.domain.Member;
import com.safecornerscoffee.jwtsample.domain.Role;
import com.safecornerscoffee.jwtsample.security.MemberDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WithMockMemberDetailsSecurityContextFactory implements WithSecurityContextFactory<WithMockMemberDetails> {

    @Override
    public SecurityContext createSecurityContext(WithMockMemberDetails withMockMemberDetails) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        Member member = createMember(withMockMemberDetails);

        MemberDetails principal = new MemberDetails(member);

        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());

        securityContext.setAuthentication(authentication);
        return securityContext;
    }

    private Member createMember(WithMockMemberDetails withMockMemberDetails) {
        Member member = Member.builder()
                .username(withMockMemberDetails.username())
                .password(withMockMemberDetails.password())
                .email(withMockMemberDetails.email())
                .roles(Arrays.stream(withMockMemberDetails.authorities())
                        .map(Role::new).collect(Collectors.toSet()))
                .build();
        member.setId(withMockMemberDetails.id());

        return member;
    }
}
