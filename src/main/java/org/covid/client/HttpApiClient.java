package org.covid.client;

import org.covid.JsonParser;
import org.covid.dto.CasesListResponse;
import org.covid.dto.CasesResponse;
import org.covid.dto.VaccinesListResponse;
import org.covid.dto.VaccinesResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import static java.net.URLEncoder.encode;

/**
 * java.net.http based api client implementation
 */
public class HttpApiClient implements ApiClient {

    private final static String BASE_URL = "https://covid-api.mmediagroup.fr/v1";
    private final static String CASES_URL = "/cases";
    private final static String VACCINES_URL = "/vaccines";
    private final static String COUNTRY_PARAM_NAME = "country";

    private final JsonParser parser;
    private final HttpClient httpClient;

    public HttpApiClient(
        JsonParser parser,
        HttpClient httpClient
    ) {
        this.parser = parser;
        this.httpClient = httpClient;
    }

    @Override
    public CasesResponse getCasesForCountry(String country) {
        try {
            var params = buildQueryParams(Map.of(COUNTRY_PARAM_NAME, country));
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + CASES_URL + params))
                    .GET()
                    .build();
            var response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return parser.fromString(response.body(), CasesListResponse.class).cases();
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    @Override
    public VaccinesResponse getVaccinesForCountry(String country) {
        try {
            var params = buildQueryParams(Map.of(COUNTRY_PARAM_NAME, country));
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + VACCINES_URL + params))
                    .GET()
                    .build();

            var response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return parser.fromString(response.body(), VaccinesListResponse.class).vaccines();
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    /**
     * url encoding is required for countries with names consisting of multiple words, e.g.
     * "The Netherlands", "Great Britain", "Bosnia and Herzegovina" and many others
     */
    private String buildQueryParams(Map<String, String> params) {
        if (params.isEmpty()) return "";
        return "?" + params.entrySet()
            .stream()
            .map(entry -> String.join(
                "=",
                encode(entry.getKey(), StandardCharsets.UTF_8),
                encode(entry.getValue(), StandardCharsets.UTF_8)
            ))
            .collect(Collectors.joining("&"));
    }
}
