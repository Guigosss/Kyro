package com.bulk.kyro.controllers;

import com.bulk.kyro.models.user.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    private String login(Model model){
        model.addAttribute("form", new LoginForm());
        return "/auth/login";
    }
}
