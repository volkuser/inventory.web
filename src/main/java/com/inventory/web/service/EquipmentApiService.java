package com.inventory.web.service;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.inventory.web.model.Equipment;

@Service
@AllArgsConstructor
public class EquipmentApiService {

    private RestTemplate restTemplate;

    public List<Equipment> getAllByEquipmentType(Long equipmentTypeId) {
        String url = "http://localhost:8081/equipments/equipmentType/" + equipmentTypeId;
        Equipment[] listWithEntities = restTemplate.getForObject(url, Equipment[].class);
        return Arrays.asList(listWithEntities);
    }

    public Equipment getById(Long id) {
        String url = "http://localhost:8081/equipments/{id}";
        Equipment entity = restTemplate.getForObject(url, Equipment.class, id);
        return entity;
    }

    public Equipment create(Equipment entity) {
        String url = "http://localhost:8081/equipments";
        Equipment created = restTemplate.postForObject(url, entity, Equipment.class);
        return created;
    }

    public Equipment update(Long id, Equipment updated) {
        String url = "http://localhost:8081/equipments/{id}";
        restTemplate.put(url, updated, id);
        return updated;
    }

    public void delete(Long id) {
        String url = "http://localhost:8081/equipments/{id}";
        restTemplate.delete(url, id);
    }
}
