package com.inventory.web.controller;

import com.inventory.web.model.*;
import com.inventory.web.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/equipment-units")
public class IndexController {
    private final TrainingCenterApiService trainingCenterApiService;
    private final LocationApiService locationApiService;
    private final EquipmentApiService equipmentApiService;
    private final EquipmentTypeApiService equipmentTypeApiService;
    private final EquipmentUnitApiService equipmentUnitApiService;

    private IndexController(TrainingCenterApiService trainingCenterApiService, LocationApiService locationApiService,
                            EquipmentApiService equipmentApiService, EquipmentTypeApiService equipmentTypeApiService,
                            EquipmentUnitApiService equipmentUnitApiService){
        this.trainingCenterApiService = trainingCenterApiService;
        this.locationApiService = locationApiService;
        this.equipmentApiService = equipmentApiService;
        this.equipmentTypeApiService = equipmentTypeApiService;
        this.equipmentUnitApiService = equipmentUnitApiService;
    }

    private final int PAGE_LIMIT = 10;
    private int currentPageNumber = 1;
    private int elementsCount = 0;

    private int getPageCount() {
        int test = (int)Math.ceil((double) elementsCount / PAGE_LIMIT);
        return Math.max(test, 1);
    }

    private String invNumberOrSearchQuery = "";
    private int filterIsWork = 0;
    private Location filterLocation;

    @GetMapping
    private String show(Model model){
        loadDataPlus(model);
        return "index";
    }

    private void loadDataPlus(Model model){
        loadData(model);

        List<TrainingCenter> trainingCenters = trainingCenterApiService.getAll();
        model.addAttribute("trainingCenters", trainingCenters);

        List<EquipmentType> equipmentTypes = equipmentTypeApiService.getAll();
        model.addAttribute("equipmentTypes", equipmentTypes);
    }

    private void loadData(Model model){
        // list at begin
        List<EquipmentUnit> equipmentUnits = equipmentUnitApiService.getAll();

        // search
        if (!invNumberOrSearchQuery.equals("")) equipmentUnits = equipmentUnitApiService.searchByInventoryNumber(invNumberOrSearchQuery, equipmentUnits);
        model.addAttribute("searchQuery", invNumberOrSearchQuery);

        // filter
        if (filterLocation != null && filterIsWork < 3) {
            equipmentUnits = equipmentUnitApiService.getByLocation(filterLocation.getLocationId(), equipmentUnits);
            filterIsWork++;
        }

        // pages
        elementsCount = equipmentUnits.size();
        model.addAttribute("currentPageNumber", currentPageNumber);

        // list at end
        equipmentUnits = equipmentUnitApiService.getAllPaginated(currentPageNumber - 1, PAGE_LIMIT, equipmentUnits);
        model.addAttribute("equipmentUnits", equipmentUnits);
    }

    /* new */

    @PostMapping("/new-equipment-type")
    private String newEquipmentType(@RequestParam(value = "Type") String typeName){
        EquipmentType added = new EquipmentType();
        added.setEquipmentTypeName(typeName);
        equipmentTypeApiService.create(added);
        return "redirect:/equipment-units";
    }

    @PostMapping("/new-equipment")
    private String newEquipment(@RequestParam(value = "Type") Long typeId,
                                @RequestParam(value = "Manufacturer") String manufacturer,
                                @RequestParam(value = "Equipment") String equipment){
        Equipment added = new Equipment();
        added.setEquipmentType(equipmentTypeApiService.getById(typeId));
        added.setManufacturer(manufacturer);
        added.setModelName(equipment);
        equipmentApiService.create(added);
        return "redirect:/equipment-units";
    }

    /* sort */

    @PostMapping("/search")
    private String search(Model model, @RequestParam(value = "searchQuery") String inventoryNumber){
        invNumberOrSearchQuery = inventoryNumber;
        currentPageNumber = 1;
        loadData(model);
        return "redirect:/equipment-units";
    }

    @PostMapping("/filter")
    private String filter(Model model, @RequestParam(value = "Corpus") Long corpus, @RequestParam(value = "Audience") String audience){
        filterLocation = locationApiService.getByCenterIdAndNumber(corpus, audience);
        filterIsWork = 0;
        loadData(model);
        return "redirect:/equipment-units";
    }

    /* change page */

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
