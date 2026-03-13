package org.aneg.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WeatherResponseDto {
    private String name;
    private MainDto main;
    private List<WeatherDescriptionDto> weather;
    private SysDto sys;
}
