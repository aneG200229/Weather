package org.aneg.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class MainDto {
    private Integer temp;
    @JsonProperty("feels_like")
    private Integer feelsLike;
    private Integer humidity;
}
