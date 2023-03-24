package com.inventory.web.controller;

import com.inventory.web.model.EquipmentUnit;
import com.inventory.web.model.TrainingCenter;
import com.inventory.web.service.EquipmentUnitApiService;
import com.inventory.web.service.TrainingCenterApiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/equipment-units")
public class IndexController {
    public TrainingCenterApiService trainingCenterApiService;
    public EquipmentUnitApiService equipmentUnitApiService;

    @GetMapping
    public String show(Model model){
        loadData(model);
        return "index";
    }

    private void loadData(Model model){
        List<TrainingCenter> trainingCenters = trainingCenterApiService.getAllTrainingCenters();
        model.addAttribute("trainingCenters", trainingCenters);

        List<EquipmentUnit> equipmentUnits = equipmentUnitApiService.getAllEquipmentUnits();
        model.addAttribute("equipmentUnits", equipmentUnits);
    }
}
