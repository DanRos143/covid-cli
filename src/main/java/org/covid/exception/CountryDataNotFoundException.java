package org.covid.exception;

public class CountryDataNotFoundException extends Exception {
    public CountryDataNotFoundException(String country) {
        super("no data found for country %s, please check the input".formatted(country));
    }
}
