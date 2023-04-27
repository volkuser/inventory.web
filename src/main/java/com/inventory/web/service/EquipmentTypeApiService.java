package com.inventory.web.service;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.inventory.web.model.EquipmentType;

@Service
@AllArgsConstructor
public class EquipmentTypeApiService {

    private RestTemplate restTemplate;

    public List<EquipmentType> getAll() {
        String url = "http://localhost:8081/equipment-types";
        EquipmentType[] listWithEntities = restTemplate.getForObject(url, EquipmentType[].class);
        assert listWithEntities != null;
        return Arrays.asList(listWithEntities);
    }

    public EquipmentType getById(Long id) {
        String url = "http://localhost:8081/equipment-types/{id}";
        return restTemplate.getForObject(url, EquipmentType.class, id);
    }

    public EquipmentType create(EquipmentType entity) {
        String url = "http://localhost:8081/equipment-types";
        return restTemplate.postForObject(url, entity, EquipmentType.class);
    }

    public EquipmentType update(Long id, EquipmentType updated) {
        String url = "http://localhost:8081/equipment-types/{id}";
        restTemplate.put(url, updated, id);
        return updated;
    }

    public void delete(Long id) {
        String url = "http://localhost:8081/equipment-types/{id}";
        restTemplate.delete(url, id);
    }
}
