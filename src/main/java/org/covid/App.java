package org.covid;

import org.covid.client.HttpApiClient;
import org.covid.exception.CountryDataNotFoundException;
import org.covid.model.CovidStats;
import org.covid.repository.ConfirmedCasesFileRepository;

import java.net.http.HttpClient;
import java.nio.file.Paths;
import java.util.Scanner;

public class App {

    private static final String FILE_NAME = "historical_data.json";

    public void start() {
        try {
            var parser = new JsonParser();
            var service = new CovidStatsService(
                new HttpApiClient(parser, HttpClient.newHttpClient()),
                new ConfirmedCasesFileRepository(parser, Paths.get(FILE_NAME))
            );

            System.out.println("Enter the country name to get COVID cases:");

            var scanner = new Scanner(System.in);
            String country = scanner.nextLine();

            CovidStats info = service.getInfoForCountry(country);
            System.out.println(info.toString());
        } catch (CountryDataNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
