package org.covid.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import org.covid.JsonParser;
import org.covid.model.ConfirmedCases;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A simple file-based repository for storing and retrieving last available confirmed cases data
 * each save() rewrites file completely for simplicity
 */
public class ConfirmedCasesFileRepository implements ConfirmedCasesRepository {

    private final JsonParser parser;
    private final Path filePath;

    public ConfirmedCasesFileRepository(JsonParser parser, Path filePath) {
        this.parser = parser;
        this.filePath = filePath;
    }

    @Override
    public void save(String country, ConfirmedCases data) {
        try {
            var file = filePath.toFile();
            var allCases = loadData(file);
            allCases.put(country, data);
            parser.toFile(file, allCases);
        } catch (IOException ignored) {}
    }

    @Override
    public Optional<ConfirmedCases> findByCountry(String country) {
        var file = filePath.toFile();
        var allCases = loadData(file);
        return Optional.ofNullable(allCases.get(country));
    }

    private Map<String, ConfirmedCases> loadData(File file) {
        try {
            var typeReference = new TypeReference<Map<String, ConfirmedCases>>(){};
            return parser.fromFile(file, typeReference);
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}
