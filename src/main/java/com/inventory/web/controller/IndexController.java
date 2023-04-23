package com.inventory.web.controller;

import com.inventory.web.model.Equipment;
import com.inventory.web.model.EquipmentUnit;
import com.inventory.web.model.TrainingCenter;
import com.inventory.web.service.EquipmentUnitApiService;
import com.inventory.web.service.TrainingCenterApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/equipment-units")
public class IndexController {
    public TrainingCenterApiService trainingCenterApiService;
    public EquipmentUnitApiService equipmentUnitApiService;

    public IndexController(TrainingCenterApiService trainingCenterApiService, EquipmentUnitApiService equipmentUnitApiService){
        this.trainingCenterApiService = trainingCenterApiService;
        this.equipmentUnitApiService = equipmentUnitApiService;
    }

    private final int PAGE_LIMIT = 10;
    private int currentPageNumber = 1;
    private int elementsCount = 0;

    private int getPageCount() {
        int test = (int)Math.ceil((double) elementsCount / PAGE_LIMIT);
        pageCount = Math.max(test, 1);
        return pageCount;
    }

    private int pageCount;
    private String invNumberOrSearchQuery = "";
    private Equipment filterEquipment = null;

    @GetMapping
    public String show(Model model){
        loadDataPlus(model);
        return "index";
    }

    private void loadDataPlus(Model model){
        loadData(model);

        List<TrainingCenter> trainingCenters = trainingCenterApiService.getAll();
        model.addAttribute("trainingCenters", trainingCenters);
    }

    private void loadData(Model model){
        List<EquipmentUnit> equipmentUnits = equipmentUnitApiService.getAll();
        if (!invNumberOrSearchQuery.equals("")) equipmentUnits = equipmentUnitApiService.searchByInventoryNumber(invNumberOrSearchQuery, equipmentUnits);

        elementsCount = equipmentUnits.size();
        model.addAttribute("currentPageNumber", currentPageNumber);

        equipmentUnits = equipmentUnitApiService.getAllPaginated(currentPageNumber - 1, PAGE_LIMIT, equipmentUnits);
        model.addAttribute("equipmentUnits", equipmentUnits);
    }

    @PostMapping("/search")
    private String search(Model model, @RequestParam(value = "searchQuery") String inventoryNumber){
        if (!inventoryNumber.equals("")) invNumberOrSearchQuery = inventoryNumber;
        else invNumberOrSearchQuery = "";
        currentPageNumber = 1;
        loadData(model);
        return "redirect:/equipment-units";
    }

    @GetMapping("/next")
    private String nextPage(Model model){
        if (currentPageNumber < getPageCount()) currentPageNumber++;
        changePage(model);
        return "redirect:/equipment-units";
    }

    @GetMapping("/previous")
    private String previousPage(Model model){
        if (currentPageNumber > 1) currentPageNumber--;
        changePage(model);
        return "redirect:/equipment-units";
    }

    @GetMapping("/end")
    private String lastPage(Model model){
        currentPageNumber = getPageCount();
        changePage(model);
        return "redirect:/equipment-units";
    }

    @GetMapping("/begin")
    private String firstPage(Model model){
        currentPageNumber = 1;
        changePage(model);
        return "redirect:/equipment-units";
    }

    private void changePage(Model model){
        model.addAttribute("currentPageNumber", String.valueOf(currentPageNumber));
        loadData(model);
    }
}
