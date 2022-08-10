package org.covid.client;

import org.covid.JsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class HttpApiClientTest {

    private final JsonParser parser = new JsonParser();
    private final HttpClient httpClient = mock(HttpClient.class);
    private final HttpApiClient apiClient = new HttpApiClient(parser, httpClient);

    @Test
    public void shouldEncodeQueryParams() throws IOException, InterruptedException {
        String country = "Bosnia and Herzegovina";

        @SuppressWarnings("unchecked")
        HttpResponse<String> responseMock = mock(HttpResponse.class);
        when(responseMock.body()).thenReturn("{}");
        when(httpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(responseMock);

        apiClient.getCasesForCountry(country);

        var requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpClient).send(requestCaptor.capture(), any());

        var request = requestCaptor.getValue();
        var uri = request.uri();
        assertEquals("country=Bosnia+and+Herzegovina", uri.getQuery());
    }
}
