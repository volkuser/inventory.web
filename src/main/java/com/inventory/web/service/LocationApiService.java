package com.inventory.web.service;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.inventory.web.model.Location;

@Service
@AllArgsConstructor
public class LocationApiService {

    private RestTemplate restTemplate;

    public List<Location> getAll() {
        String url = "http://localhost:8081/locations";
        Location[] listWithEntities = restTemplate.getForObject(url, Location[].class);
        assert listWithEntities != null;
        return Arrays.asList(listWithEntities);
    }

    public Location getById(Long id) {
        String url = "http://localhost:8081/locations/{id}";
        return restTemplate.getForObject(url, Location.class, id);
    }

    public Location create(Location entity) {
        String url = "http://localhost:8081/locations";
        return restTemplate.postForObject(url, entity, Location.class);
    }

    public Location update(Long id, Location updated) {
        String url = "http://localhost:8081/locations/{id}";
        restTemplate.put(url, updated, id);
        return updated;
    }

    public void delete(Long id) {
        String url = "http://localhost:8081/locations/{id}";
        restTemplate.delete(url, id);
    }

    public Location getByCenterIdAndNumber(Long centerId, String number){
        String url = "http://localhost:8081/locations/get-by-center-and-number?trainingCenterId=" + centerId + "&locationNumber=" + number;
        return restTemplate.getForObject(url, Location.class);
    }
}
