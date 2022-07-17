package com.safecornerscoffee.jwtsample.utils;

import com.safecornerscoffee.jwtsample.security.MemberDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMemberDetailsSecurityContextFactory implements WithSecurityContextFactory<WithMemberDetails> {

    private final MemberDetailsService memberDetailsService;

    @Autowired
    public WithMemberDetailsSecurityContextFactory(MemberDetailsService memberDetailsService) {
        this.memberDetailsService = memberDetailsService;
    }

    @Override
    public SecurityContext createSecurityContext(WithMemberDetails withMemberDetails) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        UserDetails principal = memberDetailsService.loadUserByUsername(withMemberDetails.username());

        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());

        securityContext.setAuthentication(authentication);
        return securityContext;
    }
}

