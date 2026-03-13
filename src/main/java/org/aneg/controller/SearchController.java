package org.aneg.controller;

import lombok.RequiredArgsConstructor;
import org.aneg.dto.weather.LocationDto;
import org.aneg.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class SearchController {
    private final WeatherService service;

    @GetMapping("/search")
    public String searchLocation(@RequestParam("name") String name, Model model) {
        List<LocationDto> locations = service.getLocations(name);
        model.addAttribute("locations", locations);
        return "search-results";
    }
}
