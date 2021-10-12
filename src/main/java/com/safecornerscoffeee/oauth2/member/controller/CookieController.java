package com.safecornerscoffeee.oauth2.member.controller;

import com.safecornerscoffeee.oauth2.member.domain.Member;
import com.safecornerscoffeee.oauth2.member.dto.MemberDto;
import com.safecornerscoffeee.oauth2.member.exception.InvalidUsernameOrPasswordException;
import com.safecornerscoffeee.oauth2.member.exception.MemberNotFoundException;
import com.safecornerscoffeee.oauth2.member.jwt.JwtAuthenticationProvider;
import com.safecornerscoffeee.oauth2.member.repository.MemberRepository;
import com.safecornerscoffeee.oauth2.member.security.MemberDetails;
import com.safecornerscoffeee.oauth2.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
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
import java.util.stream.Collectors;

@Profile("cookie")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CookieController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping("/join")
    public ResponseEntity<Member> join(@RequestBody MemberDto dto) {
        Member member = memberService.registerMember(dto);

        return ResponseEntity.ok(member);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto loginRequest, HttpServletResponse response) {
        Member member = memberRepository.findByUsername(loginRequest.getUsername()).orElseThrow(MemberNotFoundException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new InvalidUsernameOrPasswordException();
        }

        MemberDetails memberDetails = new MemberDetails(member);
        List<String> collect = memberDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        collect.forEach(System.out::println);
        String token = jwtAuthenticationProvider.createToken(memberDetails.getUsername(), collect);

        response.setHeader("X-AUTH-TOKEN", token);

        Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); //enable when tls supported.
        response.addCookie(cookie);

        return ResponseEntity.ok(memberDetails);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        log.debug("logout() called");
        Cookie cookie = new Cookie("X-AUTH-TOKEN", null);
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
