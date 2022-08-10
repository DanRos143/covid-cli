package org.covid;

import org.covid.client.ApiClient;
import org.covid.dto.CasesResponse;
import org.covid.dto.VaccinesResponse;
import org.covid.exception.CountryDataNotFoundException;
import org.covid.model.ConfirmedCases;
import org.covid.repository.ConfirmedCasesRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CovidStatsServiceTest {

    private final ApiClient apiClient = mock(ApiClient.class);
    private final ConfirmedCasesRepository repository = mock(ConfirmedCasesRepository.class);
    private final CovidStatsService service = new CovidStatsService(apiClient, repository);

    @Test
    public void shouldThrowCountryDataNotFoundExceptionIfDataIsNotFound() {
        String country = "Non existing";
        when(apiClient.getCasesForCountry(country)).thenReturn(null);
        when(apiClient.getVaccinesForCountry(country)).thenReturn(null);
        assertThrows(
            CountryDataNotFoundException.class,
            () -> service.getInfoForCountry(country)
        );
        verify(repository, never()).findByCountry(country);
    }

    @Test
    public void shouldSaveConfirmedCases() throws CountryDataNotFoundException {
        String country = "Estonia";
        var casesResponse = new CasesResponse(
            0,
            0,
            0,
            null
        );
        when(apiClient.getCasesForCountry(country)).thenReturn(casesResponse);
        var vaccinesResponse = new VaccinesResponse(0,0);
        when(apiClient.getVaccinesForCountry(country)).thenReturn(vaccinesResponse);

        var stats = service.getInfoForCountry(country);

        var argumentCaptor = ArgumentCaptor.forClass(ConfirmedCases.class);

        verify(apiClient).getCasesForCountry(country);
        verify(apiClient).getVaccinesForCountry(country);
        verify(repository).findByCountry(country);
        verify(repository).save(eq(country), argumentCaptor.capture());

        assertNotNull(stats);

        var saved = argumentCaptor.getValue();
        assertEquals(casesResponse.confirmed(), saved.total());
        assertNull(saved.updatedAt());
    }
}
