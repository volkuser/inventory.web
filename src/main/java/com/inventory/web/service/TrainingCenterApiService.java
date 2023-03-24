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

    public List<TrainingCenter> getAllTrainingCenters() {
        String url = "http://localhost:8081/training-centers";
        TrainingCenter[] trainingCenters = restTemplate.getForObject(url, TrainingCenter[].class);
        return Arrays.asList(trainingCenters);
    }

    public TrainingCenter getTrainingCenterById(Long trainingCenterId) {
        String url = "http://localhost:8080/training-centers/{id}";
        TrainingCenter trainingCenter = restTemplate.getForObject(url, TrainingCenter.class, trainingCenterId);
        return trainingCenter;
    }

    public TrainingCenter createTrainingCenter(TrainingCenter trainingCenter) {
        String url = "http://localhost:8081/training-centers";
        TrainingCenter createdTrainingCenter = restTemplate.postForObject(url, trainingCenter, TrainingCenter.class);
        return createdTrainingCenter;
    }

    public TrainingCenter updateTrainingCenter(Long trainingCenterId, TrainingCenter trainingCenter) {
        String url = "http://localhost:8081/training-centers/{id}";
        restTemplate.put(url, trainingCenter, trainingCenterId);
        return trainingCenter;
    }

    public void deleteTrainingCenter(Long trainingCenterId) {
        String url = "http://localhost:8081/training-centers/{id}";
        restTemplate.delete(url, trainingCenterId);
    }
}
