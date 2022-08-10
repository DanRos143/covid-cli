package org.covid;

import org.covid.client.ApiClient;
import org.covid.exception.CountryDataNotFoundException;
import org.covid.model.ConfirmedCases;
import org.covid.model.CovidStats;
import org.covid.repository.ConfirmedCasesRepository;

/**
 * Fetches cases and vaccines stats from api for a given country
 * and loads last available data
 */
public class CovidStatsService {

    private final ApiClient apiClient;
    private final ConfirmedCasesRepository repository;

    public CovidStatsService(ApiClient apiClient, ConfirmedCasesRepository repository) {
        this.apiClient = apiClient;
        this.repository = repository;
    }

    public CovidStats getInfoForCountry(String country) throws CountryDataNotFoundException {
        var cases = apiClient.getCasesForCountry(country);
        var vaccines = apiClient.getVaccinesForCountry(country);

        if (cases == null || vaccines == null) {
            throw new CountryDataNotFoundException(country);
        }

        var lastAvailable = repository.findByCountry(country);
        var confirmedCases = new ConfirmedCases(
            cases.confirmed(),
            cases.updated(),
            lastAvailable.orElse(null)
        );
        repository.save(country, confirmedCases);

        return new CovidStats(
            confirmedCases,
            cases.recovered(),
            cases.deaths(),
            vaccines.vaccinated(),
            vaccines.population()
        );
    }
}
