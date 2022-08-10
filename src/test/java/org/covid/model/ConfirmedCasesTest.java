package org.covid.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.covid.model.ConfirmedCases.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfirmedCasesTest {

    @Test
    public void shouldGenerateNoCasesMessageIfNoLastDataIsAvailable() {
        var confirmedCases = new ConfirmedCases(
            10,
            LocalDateTime.now()
        );
        assertEquals(NO_CASES_MESSAGE, confirmedCases.toString());
    }

    @Test
    public void shouldGenerateConfirmedCasesMessage() {
        var lastAvailableCases = new ConfirmedCases(
            5,
            LocalDateTime.now().minusDays(1)
        );
        var confirmedCases = new ConfirmedCases(
            10,
            LocalDateTime.now(),
            lastAvailableCases
        );
        assertEquals(
            NEW_CASES_TEMPLATE.formatted(5, FORMATTER.format(lastAvailableCases.updatedAt())),
            confirmedCases.toString()
        );
    }
}
