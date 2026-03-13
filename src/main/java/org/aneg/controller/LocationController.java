package org.aneg.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aneg.model.User;
import org.aneg.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class LocationController {
    private final LocationService service;

    @PostMapping("/locations/add")
    public String addLocation(HttpServletRequest req,
                              @RequestParam("name") String name,
                              @RequestParam("lat") BigDecimal lat,
                              @RequestParam("lon") BigDecimal lon) {
        User user = (User) req.getAttribute("currentUser");

        service.saveLocation(user, name, lat, lon);
        return "redirect:/";
    }

    @PostMapping("/locations/delete")
    public String deleteLocation(@RequestParam("id") Long id) {
        service.deleteLocation(id);
        return "redirect:/";
    }
}
