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

    public Location create(Location entity) {
        String url = "http://localhost:8081/locations";
        return restTemplate.postForObject(url, entity, Location.class);
    }

    public Location getByCenterIdAndNumber(Long centerId, String number){
        String url = "http://localhost:8081/locations/get-by-center-and-number?trainingCenterId=" + centerId + "&locationNumber=" + number;
        return restTemplate.getForObject(url, Location.class);
    }
}
