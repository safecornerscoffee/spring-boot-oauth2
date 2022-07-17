package com.safecornerscoffee.jwtsample.utils;

import com.safecornerscoffee.jwtsample.security.MemberDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class WithMockMemberDetailsSecurityContextFactoryTest {

    @Test
    @WithMockMemberDetails
    void should_get_memberDetails_from_security_context() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assertThat(authentication.getPrincipal()).isInstanceOf(MemberDetails.class);
    }

    @Test
    @WithMockMemberDetails(id=1L, username = "user", password = "", email = "user@safecornerscoffee.com", authorities = {"ROLE_USER", "ROLE_ADMIN"})
    void should_return_memberDetails_same_as_annotation_notated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        MemberDetails principal = (MemberDetails) authentication.getPrincipal();

        assertThat(principal.getId()).isEqualTo(1L);
        assertThat(principal.getUsername()).isEqualTo("user");
        assertThat(principal.getPassword()).isEmpty();
        assertThat(principal.getEmail()).isEqualTo("user@safecornerscoffee.com");
        assertThat(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)).contains("ROLE_USER", "ROLE_ADMIN");
    }
}
