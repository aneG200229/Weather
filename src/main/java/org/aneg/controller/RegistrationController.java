package org.aneg.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aneg.dto.user.UserRegisterDto;
import org.aneg.exception.PasswordMismatchException;
import org.aneg.exception.UserAlreadyExistsException;
import org.aneg.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService service;

    @GetMapping("/sign-up")
    public String registerPage(Model model) {
        model.addAttribute("userRequestDto", new UserRegisterDto());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String register(@Valid @ModelAttribute UserRegisterDto userRequestDto, BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            return "sign-up";
        }

        try {
            service.register(userRequestDto);
            return "redirect:/sign-in";
        } catch (UserAlreadyExistsException | PasswordMismatchException e) {
            model.addAttribute("error", e.getMessage());
            return "sign-up";
        }

    }
}
