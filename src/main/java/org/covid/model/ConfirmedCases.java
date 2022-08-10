package org.covid.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ConfirmedCases(
    int total,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updatedAt,
    @JsonIgnore ConfirmedCases lastAvailable
) {
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static final String NO_CASES_MESSAGE = "no newly confirmed cases";
    static final String NEW_CASES_TEMPLATE = "%d newly confirmed cases since %s";

    public ConfirmedCases(int total, LocalDateTime updatedAt) {
        this(total, updatedAt, null);
    }

    @Override
    public String toString() {
        if (lastAvailable != null) {
            int newCases = total - lastAvailable.total;
            return NEW_CASES_TEMPLATE
                .formatted(newCases, FORMATTER.format(lastAvailable.updatedAt));
        }
        return NO_CASES_MESSAGE;
    }
}
