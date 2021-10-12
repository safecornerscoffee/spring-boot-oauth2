package com.safecornerscoffeee.oauth2.utils;

import com.safecornerscoffeee.oauth2.member.security.MemberDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WithMemberDetailsSecurityContextFactoryTest {

    @Test
    @WithMemberDetails(username="test")
    void test_user_have_proper_roles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        assertThat(memberDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)).contains("ROLE_ADMIN");
    }
}