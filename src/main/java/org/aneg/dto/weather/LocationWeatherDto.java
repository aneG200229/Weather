package org.aneg.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aneg.model.Location;

@Getter
@AllArgsConstructor
public class LocationWeatherDto {
    private Location location;
    private WeatherResponseDto weather;
}
