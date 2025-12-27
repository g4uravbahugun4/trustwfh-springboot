package com.portals.trustwfh.controller;

import com.portals.trustwfh.model.ConfidenceResult;
import com.portals.trustwfh.model.LoginRequest;
import com.portals.trustwfh.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wfh")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ConfidenceResult login(@RequestBody LoginRequest request) {
        return loginService.evaluate(request);
    }
}

