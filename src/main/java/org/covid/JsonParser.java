package org.covid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;

public class JsonParser {

    private final ObjectMapper mapper = JsonMapper.builder()
        .findAndAddModules()
        .build();

    public <T> T fromString(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    public <T> T fromFile(File file, TypeReference<T> typeReference) throws IOException {
        return mapper.readValue(file, typeReference);
    }

    public void toFile(File file, Object object) throws IOException {
        mapper.writeValue(file, object);
    }
}
