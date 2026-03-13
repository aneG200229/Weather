package org.aneg.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aneg.dto.weather.LocationWeatherDto;
import org.aneg.model.Location;
import org.aneg.model.User;
import org.aneg.service.LocationService;
import org.aneg.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final LocationService locationService;
    private final WeatherService weatherService;

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        User user = (User) request.getAttribute("currentUser");
        List<Location> locations = locationService.getLocations(user);
        List<LocationWeatherDto> weathers = locations.stream().map(location -> new LocationWeatherDto(
                location,
                weatherService.getWeather(location.getLatitude(), location.getLongitude())
        )).toList();

        model.addAttribute("weathers", weathers);

        return "/index";
    }
}
