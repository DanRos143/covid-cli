package org.covid.repository;

import org.covid.JsonParser;
import org.covid.model.ConfirmedCases;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfirmedCasesFileRepositoryTest {

    private final JsonParser parser = new JsonParser();

    @Test
    public void shouldCreateHistoricalFileIfItDoesNotExist(@TempDir Path tempDir) {
        var path = tempDir.resolve("test.json");
        var country = "France";
        var cases = new ConfirmedCases(0, LocalDateTime.now());

        var repository = new ConfirmedCasesFileRepository(parser, path);
        repository.save(country, cases);
        assertTrue(Files.exists(path));
    }

    @Test
    public void shouldReturnEmptyOptionalIfFilesDoesNotExist(@TempDir Path tempDir) {
        var path = tempDir.resolve("test.json");
        var country = "France";
        var repository = new ConfirmedCasesFileRepository(parser, path);

        var found = repository.findByCountry(country);
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldLoadExistingCases(@TempDir Path tempDir) throws IOException {
        var path = tempDir.resolve("test.json");
        var country = "France";
        var initialData = """
            {
                "%s": {
                    "total": %d,
                    "updatedAt": "%s"
                }
            }
            """.formatted(country, 0, "2020-01-01 12:00:00");
        Files.writeString(path, initialData);

        var repository = new ConfirmedCasesFileRepository(parser, path);

        System.out.println(Files.readString(path));
        var found = repository.findByCountry(country);
        assertTrue(found.isPresent());
    }
}
