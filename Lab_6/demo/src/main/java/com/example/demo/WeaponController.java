package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WeaponController {
    private final List<Weapon> weapons;

    @Autowired
    public WeaponController(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    @GetMapping("/weapons")
    public String showWeapons(Model model) {
        model.addAttribute("weapons", weapons);
        return "weapons";
    }
}