package com.safecornerscoffeee.oauth2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/user")
    public ResponseEntity<?> user() {
        return ResponseEntity.ok("hello, user");
    }

    @GetMapping("/manager")
    public ResponseEntity<?> manager() {
        return ResponseEntity.ok("hello, manager");
    }
}
