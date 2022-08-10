package org.covid.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CovidStats(
    ConfirmedCases confirmedCases,
    int recovered,
    int deaths,
    BigDecimal vaccinationLevelPercentage
) {
    private static final int DEFAULT_SCALE = 4;

    public CovidStats(
        ConfirmedCases confirmedCases,
        int recovered,
        int deaths,
        int vaccinated,
        int population
    ) {
        this(
            confirmedCases,
            recovered,
            deaths,
            calculateVaccinationLevelPercentage(
                vaccinated,
                population,
                DEFAULT_SCALE
            )
        );
    }

    private static BigDecimal calculateVaccinationLevelPercentage(
        int vaccinated,
        int population,
        int scale
    ) {
        if (population == 0) return BigDecimal.ZERO;
        return BigDecimal.valueOf(vaccinated)
            .divide(BigDecimal.valueOf(population), scale, RoundingMode.DOWN)
            .scaleByPowerOfTen(2);
    }

    @Override
    public String toString() {
        var builder = new StringBuilder()
            .append("confirmed: ")
            .append(this.confirmedCases.total())
            .append('\n')
            .append("recovered: ")
            .append(this.recovered)
            .append('\n')
            .append("deaths: ")
            .append(this.deaths)
            .append('\n')
            .append("vaccinated level in % of total population: ")
            .append(this.vaccinationLevelPercentage.toPlainString());

        if (this.confirmedCases.lastAvailable() != null) {
            builder.append('\n').append(this.confirmedCases);
        }

        return builder.toString();
    }
}
