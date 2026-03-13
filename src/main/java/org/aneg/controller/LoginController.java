package org.aneg.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aneg.dto.user.UserLoginDto;
import org.aneg.exception.InvalidPasswordException;
import org.aneg.exception.NotFoundException;
import org.aneg.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserService service;

    @GetMapping("/sign-in")
    public String loginForm(Model model) {
        model.addAttribute("userRequestDto", new UserLoginDto());
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String login(@Valid @ModelAttribute UserLoginDto dto, BindingResult result,
                        Model model, HttpServletResponse resp) {
        if (result.hasErrors()) {
            return "sign-in";
        }

        try {
            service.login(dto, resp);
            return "redirect:/";
        } catch (NotFoundException | InvalidPasswordException e) {

            log.error("Login error: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "sign-in";
        }
    }
}
