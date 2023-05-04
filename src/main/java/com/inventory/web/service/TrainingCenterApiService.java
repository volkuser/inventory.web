package com.inventory.web.service;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.inventory.web.model.TrainingCenter;

@Service
@AllArgsConstructor
public class TrainingCenterApiService {

    private RestTemplate restTemplate;

    public List<TrainingCenter> getAll() {
        String url = "http://localhost:8081/training-centers";
        TrainingCenter[] listWithEntities = restTemplate.getForObject(url, TrainingCenter[].class);
        assert listWithEntities != null;
        return Arrays.asList(listWithEntities);
    }

    public TrainingCenter getById(Long id) {
        String url = "http://localhost:8081/training-centers/{id}";
        return restTemplate.getForObject(url, TrainingCenter.class, id);
    }

    public TrainingCenter create(TrainingCenter entity) {
        String url = "http://localhost:8081/training-centers";
        return restTemplate.postForObject(url, entity, TrainingCenter.class);
    }

    public TrainingCenter update(Long id, TrainingCenter updated) {
        String url = "http://localhost:8081/training-centers/{id}";
        restTemplate.put(url, updated, id);
        return updated;
    }

    public void delete(Long id) {
        String url = "http://localhost:8081/training-centers/{id}";
        restTemplate.delete(url, id);
    }

    public Long getFirstId(){
        String url = "http://localhost:8081/training-centers/first-id";
        return restTemplate.getForObject(url, Long.class);
    }
}
