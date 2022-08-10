package org.covid.repository;

import org.covid.model.ConfirmedCases;

import java.util.Optional;

public interface ConfirmedCasesRepository {
    void save(String country, ConfirmedCases cases);
    Optional<ConfirmedCases> findByCountry(String country);
}
