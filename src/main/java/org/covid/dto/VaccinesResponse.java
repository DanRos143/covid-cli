package org.covid.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VaccinesResponse(
    @JsonProperty("people_vaccinated") int vaccinated,
    int population
) {}
