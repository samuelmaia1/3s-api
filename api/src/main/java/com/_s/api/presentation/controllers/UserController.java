package com._s.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class UserController {


    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Funcionou!");
    }
}
