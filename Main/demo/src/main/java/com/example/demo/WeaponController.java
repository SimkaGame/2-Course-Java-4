package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WeaponController {
    private final WeaponDAO weaponDAO;

    @Autowired
    public WeaponController(WeaponDAO weaponDAO) {
        this.weaponDAO = weaponDAO;
    }

    @GetMapping("/weapons")
    public String showWeapons(Model model) {
        model.addAttribute("weapons", weaponDAO.findAll());
        return "weapons";
    }

    @GetMapping("/fight")
    public String fight(
            @RequestParam("num1") double enemyHp,
            @RequestParam("num2") double enemyArmor,
            @RequestParam("operation") String weaponChoice,
            Model model) {
        if (weaponChoice == null || weaponChoice.trim().isEmpty()) {
            model.addAttribute("weapons", weaponDAO.findAll());
            return "fight-form";
        }
        Weapon selectedWeapon = weaponDAO.findByType(weaponChoice);
        if (selectedWeapon == null) {
            model.addAttribute("weapons", weaponDAO.findAll());
            return "fight-form";
        }

        int weaponDamage = selectedWeapon.getDamage();
        double effectiveDamage = Math.max(0, (double) weaponDamage - enemyArmor);
        double remainingHp = enemyHp - effectiveDamage;

        boolean isEnemyDefeated = remainingHp <= 0;
        String fightResult = isEnemyDefeated
                ? "Победа! Враг повержен!"
                : "Враг выжил! У него осталось " + String.format("%.2f", remainingHp) + " здоровья.";

        model.addAttribute("enemyHp", enemyHp);
        model.addAttribute("enemyArmor", enemyArmor);
        model.addAttribute("weapon", selectedWeapon);
        model.addAttribute("effectiveDamage", effectiveDamage);
        model.addAttribute("remainingHp", remainingHp);
        model.addAttribute("fightResult", fightResult);
        model.addAttribute("isEnemyDefeated", isEnemyDefeated);

        return "fight";
    }

    @GetMapping("/fight-form")
    public String showFightForm(Model model) {
        model.addAttribute("weapons", weaponDAO.findAll());
        return "fight-form";
    }

    @GetMapping("/weapon")
    public String showWeaponDetails(@RequestParam("type") String type, Model model) {
        Weapon selectedWeapon = weaponDAO.findByType(type);
        if (selectedWeapon == null) {
            return "redirect:/weapons";
        }
        model.addAttribute("weapon", selectedWeapon);
        return "weapon";
    }

    @GetMapping("/add-weapon")
    public String showAddWeaponForm(Model model) {
        return "add-weapon";
    }

    @PatchMapping("/add-weapon")
    public String addWeapon(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "damage", required = false) Integer damage,
            @RequestParam(value = "material", required = false) String material,
            Model model) {
        if (type == null || type.trim().isEmpty() || damage == null || damage < 0 || material == null || material.trim().isEmpty()) {
            return "add-weapon";
        }
        if (weaponDAO.findByType(type) != null) {
            return "add-weapon";
        }

        Weapon newWeapon = new CustomWeapon(type, damage, material);
        weaponDAO.save(newWeapon);
        return "redirect:/weapons";
    }

    @GetMapping("/edit-weapon")
    public String showEditWeaponForm(@RequestParam("type") String type, Model model) {
        Weapon selectedWeapon = weaponDAO.findByType(type);
        if (selectedWeapon == null) {
            return "redirect:/weapons";
        }
        model.addAttribute("weapon", selectedWeapon);
        return "edit-weapon";
    }

    @PatchMapping("/weapon")
    public String updateWeapon(
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "damage", required = false) Integer damage,
            @RequestParam(value = "material", required = false) String material,
            Model model) {
        Weapon weaponToUpdate = weaponDAO.findByType(type);
        if (weaponToUpdate == null) {
            return "edit-weapon";
        }
        if (damage != null && damage < 0 || material != null && material.trim().isEmpty()) {
            model.addAttribute("weapon", weaponToUpdate);
            return "edit-weapon";
        }

        String newMaterial = material != null && !material.trim().isEmpty() ? material : weaponToUpdate.getMaterial();
        int newDamage = damage != null ? damage : weaponToUpdate.getDamage();
        Weapon updatedWeapon = new CustomWeapon(type, newDamage, newMaterial);
        weaponDAO.update(type, updatedWeapon);
        return "redirect:/weapons";
    }

    @DeleteMapping("/weapon")
    public String deleteWeapon(@RequestParam("type") String type, Model model) {
        if (weaponDAO.findByType(type) == null) {
            return "redirect:/weapons";
        }
        weaponDAO.delete(type);
        return "redirect:/weapons";
    }
}