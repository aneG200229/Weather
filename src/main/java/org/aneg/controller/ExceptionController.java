package org.aneg.controller;

import org.aneg.exception.OpenWeatherException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(OpenWeatherException.class)
    public String openWeatherException(Model model) {
        model.addAttribute("error", "Weather service is unavailable");
        return "error";
    }
}
