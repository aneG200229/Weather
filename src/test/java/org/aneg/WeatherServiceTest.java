package org.aneg;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aneg.dto.weather.LocationDto;
import org.aneg.dto.weather.WeatherResponseDto;
import org.aneg.exception.OpenWeatherException;
import org.aneg.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {
    @Mock
    private HttpClient client;

    private ObjectMapper mapper;

    @Mock
    private HttpResponse<String> response;

    @InjectMocks
    private WeatherService service;

    private static final int BAD_REQUEST = 400;


    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        ReflectionTestUtils.setField(service, "apiKey", "test");
        ReflectionTestUtils.setField(service, "mapper", mapper);
    }

    @Test
    public void getLocation_whenValidCity_returnLocations() throws IOException, InterruptedException {
        String json = """
                [
                    {
                        "name": "London",
                        "lat": 51.5073219,
                        "lon": -0.1276474,
                        "country": "GB",
                        "state": "England"
                    }
                ]
                """;

        when(response.body()).thenReturn(json);
        doReturn(response).when(client).send(any(), any());

        List<LocationDto> locations = service.getLocations("London");

        assertNotNull(locations);
        assertEquals(1, locations.size());
        assertEquals("London", locations.get(0).getName());
    }

    @Test
    public void getWeather_whenValidCoordinates_returnsWeather() throws IOException, InterruptedException {
        String json = """
                {
                    "weather": [
                        {
                            "description": "overcast clouds",
                            "icon": "04d"
                        }
                    ],
                    "main": {
                        "temp": 11.71,
                        "feels_like": 11.03,
                        "humidity": 80
                    },
                    "sys": {
                        "country": "GB"
                    },
                    "name": "London"
                }
                """;

        when(response.body()).thenReturn(json);
        doReturn(response).when(client).send(any(),any());

        WeatherResponseDto responseDto = service.getWeather(new BigDecimal("51.5073219"), new BigDecimal("-0.1276474"));

        assertNotNull(responseDto);
        assertEquals("London", responseDto.getName());
    }

    @Test
    public void getWeather_whenInvalidCoordinates_throwsOpenWeatherException() throws IOException, InterruptedException {
        when(response.statusCode()).thenReturn(BAD_REQUEST);
        doReturn(response).when(client).send(any(),any());

        assertThrows(OpenWeatherException.class, () -> service.getWeather(new BigDecimal("123"), new BigDecimal("-123")));
    }
}
