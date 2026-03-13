package org.aneg.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aneg.dto.weather.LocationDto;
import org.aneg.dto.weather.WeatherResponseDto;
import org.aneg.exception.OpenWeatherException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

    private final HttpClient client;
    private final ObjectMapper mapper;
    private static final int BAD_REQUEST = 400;


    @Value("${weather.api.key}")
    private String apiKey;


    public List<LocationDto> getLocations(String name) {
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String
                            .format("https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=%s",
                                    URLEncoder.encode(name, StandardCharsets.UTF_8), apiKey)))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), new TypeReference<List<LocationDto>>() {
            });

        } catch (IOException | InterruptedException e) {
            log.error("OpenWeather Api not answer");
            throw new OpenWeatherException("OpenWeather Api not answer");
        }
    }

    public WeatherResponseDto getWeather(BigDecimal lat, BigDecimal lon) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String
                            .format("https://api.openweathermap.org/data/2.5/weather?units=metric&lat=%f&lon=%f&appid=%s"
                                    , lat, lon, apiKey)))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == BAD_REQUEST) {
                log.error("OpenWeather Api error: {}", response.statusCode());
                throw new OpenWeatherException("OpenWeather Api error: " + response.statusCode());
            }

            return mapper.readValue(response.body(), WeatherResponseDto.class);
        } catch (IOException | InterruptedException e) {
            log.error("OpenWeather Api not answer");
            throw new OpenWeatherException("OpenWeather Api not answer");
        }
    }
}

