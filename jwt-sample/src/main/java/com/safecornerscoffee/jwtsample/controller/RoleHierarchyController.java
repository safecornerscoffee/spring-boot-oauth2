package com.safecornerscoffee.jwtsample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleHierarchyController {

    @GetMapping("/role/user")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("hello, user");
    }

    @GetMapping("/role/manager")
    public ResponseEntity<String> manager() {
        return ResponseEntity.ok("hello, manager");
    }
}
