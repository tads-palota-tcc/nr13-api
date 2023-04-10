package br.com.smartnr.nr13api.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plants")
public class PlantController {

    @GetMapping
    public String plant() {
        return "plant works!";
    }
}
