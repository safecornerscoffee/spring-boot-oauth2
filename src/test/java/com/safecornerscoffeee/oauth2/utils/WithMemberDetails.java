package com.safecornerscoffeee.oauth2.utils;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMemberDetailsSecurityContextFactory.class)
public @interface WithMemberDetails {
    long id() default 1L;
    String username() default "user";
    String password() default "";
    String email() default "user@safecornerscoffee.com";
    String[] authorities() default "ROLE_USER";
}



