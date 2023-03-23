package com.inventory.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/equipment-units")
public class IndexController {
    @GetMapping
    public String show(Model model){
        return "index";
    }
}
