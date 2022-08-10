package org.covid.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CasesResponse(
    int confirmed,
    int recovered,
    int deaths,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updated
) {}
