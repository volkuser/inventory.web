package com.inventory.web.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.inventory.web.model.EquipmentType;
import com.inventory.web.model.EquipmentUnit;
import com.inventory.web.model.Location;
import com.inventory.web.service.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/equipment-units/{id}")
public class EquipmentController {
    private final EquipmentUnitApiService equipmentUnitApiService;
    private final EquipmentApiService equipmentApiService;
    private final TrainingCenterApiService trainingCenterApiService;
    private final LocationApiService locationApiService;
    private final EquipmentTypeApiService equipmentTypeApiService;

    @GetMapping
    private String show(Model model, @PathVariable("id") Long id){
        loadData(model, id, (long) -1);
        return "equipment";
    }

    private void loadData(Model model, Long id, Long typeId) {
        // load static selects
        model.addAttribute("equipmentTypes", equipmentTypeApiService.getAll());
        model.addAttribute("trainingCenters", trainingCenterApiService.getAll());

        EquipmentUnit unit;
        EquipmentType type;

        if (id == -1) { // if new item
            unit = new EquipmentUnit(); // stub

            // default input, where can't do without this input
            unit.setEquipmentUnitId((long) -1);
            Location location = new Location();
            location.setTrainingCenter(trainingCenterApiService.getById(trainingCenterApiService.getFirstId()));
            unit.setLocation(location);
            unit.setOnState(false);

            type = (typeId != -1) ? equipmentTypeApiService.getById(typeId)
                    : equipmentTypeApiService.getById(equipmentTypeApiService.getFirstId());
        } else { // if current item
            unit = equipmentUnitApiService.getById(id);

            model.addAttribute("corpus", unit.getLocation().getTrainingCenter());
            model.addAttribute("audience", unit.getLocation());

            type = (typeId != -1) ? equipmentTypeApiService.getById(typeId) : unit.getEquipment().getEquipmentType();
        }

        model.addAttribute("current", unit);

        model.addAttribute("currentEquipmentType", type);
        model.addAttribute("equipments", equipmentApiService.getAllByEquipmentType(type.getEquipmentTypeId()));
    }

    @PostMapping
    private String save(@PathVariable(value = "id") Long id, @RequestParam(value = "invNumber") String inventoryNumber,
                        @RequestParam(value = "corpus") Long corpus, @RequestParam(value = "audience") String audience,
                        @RequestParam(value = "equipment") Long equipment,
                        @RequestParam(value = "onStateAsString", defaultValue = "off") String onStateAsString){
        EquipmentUnit current = id != -1 ? equipmentUnitApiService.getById(id) : new EquipmentUnit();

        current.setEquipmentUnitId(id);
        current.setInventoryNumber(inventoryNumber);
        current.setEquipment(equipmentApiService.getById(equipment));
        current.setLocation(locationApiService.getByCenterIdAndNumber(corpus, audience));
        current.setOnState(onStateAsString.equals("on"));

        UUID uuid;
        if (id != -1) {
            equipmentUnitApiService.update(id, current);
            uuid = current.getGuidCode();
        }
        else {
            uuid = UUID.randomUUID();
            current.setGuidCode(uuid);

            equipmentUnitApiService.create(current);
        }

        return "redirect:/equipment-units/" + equipmentUnitApiService.getByGuid(uuid).getEquipmentUnitId();
    }

    @GetMapping("/delete")
    private String delete(@PathVariable(value = "id") Long id) {
        equipmentUnitApiService.delete(id);

        return "redirect:/equipment-units";
    }

    @PostMapping("/type-change")
    private String typeChanged(Model model, @PathVariable(value = "id") Long id, @RequestParam(value = "type") Long typeId){
        loadData(model, id, typeId);

        return "equipment";
    }

    @GetMapping("/qr-code")
    private String generateQrCode(@PathVariable(value = "id") Long id, HttpServletResponse response) throws Exception {
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(generateQrCodeByGuidCode(id), "png", outputStream);

        response.setContentType("image/png");
        outputStream.flush();
        outputStream.close();

        return "redirect:/equipment-units/" + id;
    }

    @GetMapping("/save-qr-code")
    private void saveQrCode(@PathVariable(value = "id") Long id, HttpServletResponse response) throws Exception {
        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "attachment;filename=" + equipmentUnitApiService.getById(id).getInventoryNumber() + ".png");

        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(generateQrCodeByGuidCode(id), "png", outputStream);

        outputStream.flush();
        outputStream.close();
    }

    private BufferedImage generateQrCodeByGuidCode(Long id) throws WriterException {
        UUID guidCode = equipmentUnitApiService.getById(id).getGuidCode();
        String text = guidCode.toString();
        BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, 343, 343);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
