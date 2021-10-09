package com.safecornerscoffeee.oauth2.member;

import com.safecornerscoffeee.oauth2.member.domain.Member;
import com.safecornerscoffeee.oauth2.member.domain.Privilege;
import com.safecornerscoffeee.oauth2.member.domain.Role;
import com.safecornerscoffeee.oauth2.member.repository.MemberRepository;
import com.safecornerscoffeee.oauth2.member.repository.PrivilegeRepository;
import com.safecornerscoffeee.oauth2.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        createRoleIfNotFound("ROLE_ADMIN", Set.of(readPrivilege, writePrivilege));
        createRoleIfNotFound("ROLE_USER", Set.of(readPrivilege));

        createTestUser();

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        return privilegeRepository.findByName(name)
                .orElseGet(() -> {
                    Privilege privilege = new Privilege(name);
                    return privilegeRepository.save(privilege);
                });
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role(name);
                    role.setPrivileges(privileges);
                    return roleRepository.save(role);
                });
    }

    private void createTestUser() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new IllegalStateException("Could not find a 'ROLE_ADMIN'"));
        Member member = Member.builder()
                .username("test")
                .password(passwordEncoder.encode("test"))
                .email("test@safecornerscoffee.com")
                .role(adminRole)
                .enabled(true)
                .build();
        memberRepository.save(member);
    }
}
