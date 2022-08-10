package org.covid.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CovidStatsTest {

    @Test
    public void shouldCalculateVaccinationLevelPercentage() {
        int vaccinated = 1;
        int population = 10;

        var stats = new CovidStats(
            new ConfirmedCases(0, LocalDateTime.now()),
            1,
            1,
            vaccinated,
            population
        );

        assertEquals(0, BigDecimal.TEN.compareTo(stats.vaccinationLevelPercentage()));

        var zeroStats = new CovidStats(
            new ConfirmedCases(0, LocalDateTime.now()),
            0,
            0,
            0,
            0
        );
        assertEquals(BigDecimal.ZERO, zeroStats.vaccinationLevelPercentage());
    }

    @Test
    public void shouldIncludeNewCasesIfLastDataIsAvailable() {
        int totalConfirmedCases = 10;
        int lastAvailableTotalConfirmedCases = 7;
        int recovered = 0;
        int deaths = 0;
        var vaccinationLevelPercentage = "86.10";
        var lastDate = "2020-01-01 12:00:00";

        var lastAvailableConfirmCases = new ConfirmedCases(
            lastAvailableTotalConfirmedCases,
            LocalDateTime.parse(lastDate, ConfirmedCases.FORMATTER)
        );

        var stats = new CovidStats(
            new ConfirmedCases(totalConfirmedCases, LocalDateTime.now(), lastAvailableConfirmCases),
            recovered,
            deaths,
            new BigDecimal(vaccinationLevelPercentage)
        );

        var expectedString = """
            confirmed: %d
            recovered: %d
            deaths: %d
            vaccinated level in %% of total population: %s
            %d newly confirmed cases since %s""".formatted(
            totalConfirmedCases,
            recovered,
            deaths,
            vaccinationLevelPercentage,
            totalConfirmedCases - lastAvailableTotalConfirmedCases,
            lastDate
        );

        assertEquals(expectedString, stats.toString());
    }

    @Test
    public void shouldNotIncludeNewCasesIfLastDataIsNotAvailable() {
        int totalConfirmedCases = 10;
        int recovered = 0;
        int deaths = 0;
        var vaccinationLevelPercentage = "86.10";

        var stats = new CovidStats(
            new ConfirmedCases(totalConfirmedCases, LocalDateTime.now()),
            recovered,
            deaths,
            new BigDecimal(vaccinationLevelPercentage)
        );

        var expectedString = """
            confirmed: %d
            recovered: %d
            deaths: %d
            vaccinated level in %% of total population: %s""".formatted(
            totalConfirmedCases, recovered, deaths, vaccinationLevelPercentage
        );

        assertEquals(expectedString, stats.toString());
    }
}
