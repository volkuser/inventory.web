package com.inventory.web.service;

import com.inventory.web.model.EquipmentUnit;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

    @Service
    @AllArgsConstructor
    public class EquipmentUnitApiService {

        private RestTemplate restTemplate;

        public List<EquipmentUnit> getAll() {
            String url = "http://localhost:8081/equipment-units";
            EquipmentUnit[] listWithEntities = restTemplate.getForObject(url, EquipmentUnit[].class);
            assert listWithEntities != null;
            return Arrays.asList(listWithEntities);
        }

        public EquipmentUnit getById(Long id) {
            String url = "http://localhost:8081/equipment-units/{id}";
            return restTemplate.getForObject(url, EquipmentUnit.class, id);
        }

        public EquipmentUnit getByGuid(UUID guidCode) {
            String url = "http://localhost:8081/equipment-units/guid/" + guidCode;
            return restTemplate.getForObject(url, EquipmentUnit.class);
        }

        public EquipmentUnit create(EquipmentUnit entity) {
            String url = "http://localhost:8081/equipment-units";
            return restTemplate.postForObject(url, entity, EquipmentUnit.class);
        }

        public EquipmentUnit update(Long id, EquipmentUnit updated) {
            String url = "http://localhost:8081/equipment-units/{id}";
            restTemplate.put(url, updated, id);
            return updated;
        }

        public void delete(Long id) {
            String url = "http://localhost:8081/equipment-units/{id}";
            restTemplate.delete(url, id);
        }

        public List<EquipmentUnit> getAllPaginated(int offset, int limit, List<EquipmentUnit> equipmentUnits) {
            String url = "http://localhost:8081/equipment-units/exist-paginated?offset=" + offset + "&limit=" + limit;
            EquipmentUnit[] listWithEntities = restTemplate.postForObject(url, equipmentUnits, EquipmentUnit[].class);
            assert listWithEntities != null;
            return Arrays.asList(listWithEntities);
        }

        public List<EquipmentUnit> searchByInventoryNumber(String query, List<EquipmentUnit> equipmentUnits) {
            String url = "http://localhost:8081/equipment-units/search?query=" + query;
            EquipmentUnit[] listWithEntities = restTemplate.postForObject(url, equipmentUnits, EquipmentUnit[].class);
            assert listWithEntities != null;
            return Arrays.asList(listWithEntities);
        }

        public List<EquipmentUnit> getByLocation(Long locationId, List<EquipmentUnit> equipmentUnits) {
            String url = "http://localhost:8081/equipment-units/by-location/" + locationId;
            EquipmentUnit[] listWithEntities = restTemplate.postForObject(url, equipmentUnits, EquipmentUnit[].class);
            assert listWithEntities != null;
            return Arrays.asList(listWithEntities);
        }

    }

