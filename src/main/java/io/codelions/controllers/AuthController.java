package io.codelions.controllers;

import io.codelions.dto.BaseResponse;
import io.codelions.dto.CreateCardRequest;
import io.codelions.dto.LoginRequest;
import io.codelions.dto.LoginResponse;
import io.codelions.services.CardService;
import io.codelions.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CardService cardService;
    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> registerCard(@RequestBody CreateCardRequest request) {
        return ResponseEntity.ok(cardService.registerCard(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginService.login(request));
    }
}
