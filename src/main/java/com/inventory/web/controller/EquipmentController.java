package com.inventory.web.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.inventory.web.model.Equipment;
import com.inventory.web.model.EquipmentType;
import com.inventory.web.model.EquipmentUnit;
import com.inventory.web.model.Location;
import com.inventory.web.service.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/equipment-units/{id}")
public class EquipmentController {
    public EquipmentUnitApiService equipmentUnitApiService;
    public EquipmentApiService equipmentApiService;
    public TrainingCenterApiService trainingCenterApiService;
    public LocationApiService locationApiService;
    public EquipmentTypeApiService equipmentTypeApiService;

    @GetMapping
    public String show(Model model, @PathVariable("id") Long id){
        loadData(model, id, (long) -1);
        return "equipment";
    }

    private void loadData(Model model, Long id, Long typeId) {
        if (id == -1) return; /// TODO if new model
        EquipmentUnit unit = equipmentUnitApiService.getById(id);
        model.addAttribute("current", unit);

        model.addAttribute("equipmentTypes", equipmentTypeApiService.getAll());

        EquipmentType type = (typeId != -1) ? equipmentTypeApiService.getById(typeId) : unit.getEquipment().getEquipmentType();
        model.addAttribute("currentEquipmentType", type);
        model.addAttribute("equipments", equipmentApiService.getAllByEquipmentType(type.getEquipmentTypeId()));

        model.addAttribute("trainingCenters", trainingCenterApiService.getAll());
        /// TODO realize location by training center
        model.addAttribute("audience", unit.getLocation());
    }

    @PostMapping
    private String save(@PathVariable(value = "id") Long id, @ModelAttribute(value = "invNumber") String inventoryNumber,
                        /*@ModelAttribute(value = "audience") String audience, */@RequestParam(value = "equipment") String equipment,
                        @RequestParam(value = "onStateAsString", defaultValue = "off") String onStateAsString){
        EquipmentUnit current = equipmentUnitApiService.getById(id);

        current.setInventoryNumber(inventoryNumber);
        current.setEquipment(equipmentApiService.getById(Long.valueOf(equipment)));
        //current.setLocation(locationApiService.getById(Long.parseLong(audience)));
        current.setOnState(onStateAsString.equals("on"));

        equipmentUnitApiService.update(id, current);

        return "redirect:/equipment-units/" + id;
    }

    @PostMapping("/type-change")
    private String typeChanged(Model model, @PathVariable(value = "id") Long id, @RequestParam(value = "type") Long typeId){
        loadData(model, id, typeId);

        return "equipment";
    }

    @GetMapping("/qr-code")
    public void generateQrCode(@PathVariable(value = "id") Long id, HttpServletResponse response) throws Exception {
        UUID guidCode = equipmentUnitApiService.getById(id).getGuidCode();
        String text = guidCode.toString();
        BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, 343, 343);

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(qrImage, "png", outputStream);

        response.setContentType("image/png");
        outputStream.flush();
        outputStream.close();
    }
}
