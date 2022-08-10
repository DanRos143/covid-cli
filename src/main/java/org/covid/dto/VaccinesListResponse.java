package org.covid.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Covid-19-API returns stats for states as well as the total for a whole country ("All")
 * everything except total is ignored
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VaccinesListResponse(@JsonProperty("All") VaccinesResponse vaccines) {}
