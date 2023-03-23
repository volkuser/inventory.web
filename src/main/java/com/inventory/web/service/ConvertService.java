package com.inventory.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ConvertService {

    private final ObjectMapper objectMapper;

    // from json
    public <T> T convertToEntity(String json, Class<T> entityClass) throws JsonProcessingException {
        return objectMapper.readValue(json, entityClass);
    }
}
