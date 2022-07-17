package com.safecornerscoffee.jwtsample.controller;

import com.safecornerscoffee.jwtsample.domain.Member;
import com.safecornerscoffee.jwtsample.dto.MemberDto;
import com.safecornerscoffee.jwtsample.exception.InvalidUsernameOrPasswordException;
import com.safecornerscoffee.jwtsample.exception.MemberNotFoundException;
import com.safecornerscoffee.jwtsample.jwt.JwtCookieAuthenticationProvider;
import com.safecornerscoffee.jwtsample.repository.MemberRepository;
import com.safecornerscoffee.jwtsample.security.MemberDetails;
import com.safecornerscoffee.jwtsample.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "cookie")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CookieController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtCookieAuthenticationProvider jwtCookieAuthenticationProvider;

    private static final String X_AUTH_TOKEN = "X-AUTH-TOKEN";

    @PostMapping("/join")
    public ResponseEntity<Member> join(@RequestBody MemberDto dto) {
        Member member = memberService.registerMember(dto);

        return ResponseEntity.ok(member);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberDetails> login(@RequestBody MemberDto loginRequest, HttpServletResponse response) {
        Member member = memberRepository.findByUsername(loginRequest.getUsername()).orElseThrow(MemberNotFoundException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new InvalidUsernameOrPasswordException();
        }

        MemberDetails memberDetails = new MemberDetails(member);
        List<String> collect = memberDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        collect.forEach(log::info);
        String token = jwtCookieAuthenticationProvider.createToken(memberDetails.getUsername(), collect);

        response.setHeader(X_AUTH_TOKEN, token);

        Cookie cookie = new Cookie(X_AUTH_TOKEN, token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); //enable when tls supported.
        response.addCookie(cookie);

        return ResponseEntity.ok(memberDetails);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        log.debug("logout() called");
        Cookie cookie = new Cookie(X_AUTH_TOKEN, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok("logout");
    }

    @GetMapping("/info")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MemberDetails> getInfo(@AuthenticationPrincipal MemberDetails memberDetails) {
        return ResponseEntity.ok(memberDetails);
    }

}
