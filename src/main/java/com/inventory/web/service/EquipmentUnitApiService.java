package com.inventory.web.service;

import com.inventory.web.model.EquipmentUnit;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

    @Service
    @AllArgsConstructor
    public class EquipmentUnitApiService {

        private RestTemplate restTemplate;

        public void Task(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public List<EquipmentUnit> getAllEquipmentUnits() {
            String url = "http://localhost:8081/equipment-units";
            EquipmentUnit[] equipmentUnit = restTemplate.getForObject(url, EquipmentUnit[].class);
            return Arrays.asList(equipmentUnit);
        }

            public EquipmentUnit getEquipmentUnitById(Long equipmentUnitId) {
            String url = "http://localhost:8081/equipment-units/{id}";
                EquipmentUnit equipmentUnit = restTemplate.getForObject(url, EquipmentUnit.class, equipmentUnitId);
            return equipmentUnit;
        }

        public EquipmentUnit createTrainingCenter(EquipmentUnit equipmentUnit) {
            String url = "http://localhost:8081/equipment-units";
            EquipmentUnit createdTrainingCenter = restTemplate.postForObject(url, equipmentUnit, EquipmentUnit.class);
            return createdTrainingCenter;
        }

        public EquipmentUnit updateEquipmentUnit(Long equipmentUnitId, EquipmentUnit equipmentUnit) {
            String url = "http://localhost:8081/equipment-units/{id}";
            restTemplate.put(url, equipmentUnit, equipmentUnitId);
            return equipmentUnit;
        }

        public void deleteEquipmentUnit(Long equipmentUnitId) {
            String url = "http://localhost:8081/equipment-units/{id}";
            restTemplate.delete(url, equipmentUnitId);
        }
    }

