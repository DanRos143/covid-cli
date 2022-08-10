package org.covid.client;

import org.covid.dto.CasesResponse;
import org.covid.dto.VaccinesResponse;

public interface ApiClient {
    CasesResponse getCasesForCountry(String country);
    VaccinesResponse getVaccinesForCountry(String country);
}
