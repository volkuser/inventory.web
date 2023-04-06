package com.inventory.web.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.inventory.web.model.EquipmentType;
import com.inventory.web.model.EquipmentUnit;
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
    public EquipmentUnitApiService equipmentUnitApiService;
    public EquipmentApiService equipmentApiService;
    public TrainingCenterApiService trainingCenterApiService;
    public LocationApiService locationApiService;
    public EquipmentTypeApiService equipmentTypeApiService;

    @GetMapping
    public String show(Model model, @PathVariable("id") Long id, HttpServletResponse response){
        loadData(model, id, response, (long) -1);
        return "equipment";
    }

    private void loadData(Model model, Long id, HttpServletResponse response, Long typeId) {
        if (id == -1) return; /// TODO if new model
        EquipmentUnit unit = equipmentUnitApiService.getById(id);
        model.addAttribute("current", unit);

        model.addAttribute("equipmentTypes", equipmentTypeApiService.getAll());

        EquipmentType type = (typeId != -1) ? equipmentTypeApiService.getById(typeId) : unit.getEquipment().getEquipmentType();
        model.addAttribute("currentEquipmentType", type);
        model.addAttribute("equipments", equipmentApiService.getAllByEquipmentType(type.getEquipmentTypeId()));

        model.addAttribute("trainingCenters", trainingCenterApiService.getAll());
        /// TODO realize location by training center
        model.addAttribute("audience", unit.getLocation().getLocationNumber());
    }

    @PostMapping
    private String typeChanged(Model model, @PathVariable(value = "id") Long id, @RequestParam(value = "type") Long typeId, HttpServletResponse response){
        loadData(model, id, response, typeId);

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
