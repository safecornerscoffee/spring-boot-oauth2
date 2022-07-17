package com.safecornerscoffee.jwtsample.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

@Slf4j
class JwtCookieAuthenticationProviderTest {

    @Test
    void generate_random_secret_key() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String encode = Encoders.BASE64URL.encode(secretKey.getEncoded());
        log.info("BASE64URL: " + encode);
    }

}