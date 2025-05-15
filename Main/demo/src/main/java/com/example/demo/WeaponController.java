package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WeaponController {
    private final List<Weapon> weapons;

    @Autowired
    public WeaponController(List<Weapon> initialWeapons) {
        this.weapons = new ArrayList<>(initialWeapons);
    }

    @GetMapping("/weapons")
    public String showWeapons(Model model) {
        model.addAttribute("weapons", weapons);
        return "weapons";
    }

    @GetMapping("/fight")
    public String fight(
            @RequestParam("num1") double enemyHp,
            @RequestParam("num2") double enemyArmor,
            @RequestParam("operation") String weaponChoice,
            Model model) {
        try {
            Weapon selectedWeapon = null;
            for (Weapon weapon : weapons) {
                if (weapon.getType().equalsIgnoreCase(weaponChoice)) {
                    selectedWeapon = weapon;
                    break;
                }
            }
            if (selectedWeapon == null) {
                model.addAttribute("errorMessage", "Недопустимое оружие: " + weaponChoice);
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
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка: " + e.getMessage());
            return "fight-form";
        }
    }

    @GetMapping("/fight-form")
    public String showFightForm(Model model) {
        model.addAttribute("weapons", weapons);
        return "fight-form";
    }

    @GetMapping("/weapon")
    public String showWeaponDetails(@RequestParam("type") String type, Model model) {
        Weapon selectedWeapon = null;
        for (Weapon weapon : weapons) {
            if (weapon.getType().equalsIgnoreCase(type)) {
                selectedWeapon = weapon;
                break;
            }
        }
        if (selectedWeapon == null) {
            model.addAttribute("errorMessage", "Оружие не найдено: " + type);
            return "redirect:/weapons";
        }
        model.addAttribute("weapon", selectedWeapon);
        return "weapon";
    }

    @GetMapping("/add-weapon")
    public String showAddWeaponForm(Model model) {
        return "add-weapon";
    }

    @PostMapping("/add-weapon")
    public String addWeapon(
            @RequestParam("type") String type,
            @RequestParam("damage") int damage,
            @RequestParam("material") String material,
            Model model) {
        if (type == null || type.trim().isEmpty()) {
            model.addAttribute("errorMessage", "Тип оружия не может быть пустым");
            return "add-weapon";
        }
        if (damage < 0) {
            model.addAttribute("errorMessage", "Урон не может быть отрицательным");
            return "add-weapon";
        }
        if (material == null || material.trim().isEmpty()) {
            model.addAttribute("errorMessage", "Материал не может быть пустым");
            return "add-weapon";
        }

        for (Weapon weapon : weapons) {
            if (weapon.getType().equalsIgnoreCase(type)) {
                model.addAttribute("errorMessage", "Оружие с типом '" + type + "' уже существует");
                return "add-weapon";
            }
        }

        Weapon newWeapon = new CustomWeapon(type, damage, material);
        weapons.add(newWeapon);
        return "redirect:/weapons";
    }

    @GetMapping("/edit-weapon")
    public String showEditWeaponForm(@RequestParam("type") String type, Model model) {
        Weapon selectedWeapon = null;
        for (Weapon weapon : weapons) {
            if (weapon.getType().equalsIgnoreCase(type)) {
                selectedWeapon = weapon;
                break;
            }
        }
        if (selectedWeapon == null) {
            model.addAttribute("errorMessage", "Оружие не найдено: " + type);
            return "redirect:/weapons";
        }
        model.addAttribute("weapon", selectedWeapon);
        return "edit-weapon";
    }

    @PostMapping("/weapon")
    public String updateWeapon(
            @RequestParam("type") String type,
            @RequestParam(value = "damage", required = false) Integer damage,
            @RequestParam(value = "material", required = false) String material,
            Model model) {
        Weapon weaponToUpdate = null;
        int index = -1;
        for (int i = 0; i < weapons.size(); i++) {
            if (weapons.get(i).getType().equalsIgnoreCase(type)) {
                weaponToUpdate = weapons.get(i);
                index = i;
                break;
            }
        }

        if (weaponToUpdate == null) {
            model.addAttribute("errorMessage", "Оружие не найдено: " + type);
            return "edit-weapon";
        }

        if (damage != null && damage < 0) {
            model.addAttribute("errorMessage", "Урон не может быть отрицательным");
            model.addAttribute("weapon", weaponToUpdate);
            return "edit-weapon";
        }
        if (material != null && (material.trim().isEmpty())) {
            model.addAttribute("errorMessage", "Материал не может быть пустым");
            model.addAttribute("weapon", weaponToUpdate);
            return "edit-weapon";
        }

        String newMaterial = material != null ? material : weaponToUpdate.getMaterial();
        int newDamage = damage != null ? damage : weaponToUpdate.getDamage();
        Weapon updatedWeapon = new CustomWeapon(type, newDamage, newMaterial);
        weapons.set(index, updatedWeapon);
        return "redirect:/weapons";
    }

    @PostMapping("/weapon/delete")
    public String deleteWeapon(@RequestParam("type") String type, Model model) {
        boolean removed = weapons.removeIf(weapon -> weapon.getType().equalsIgnoreCase(type));
        if (!removed) {
            model.addAttribute("errorMessage", "Не удалось удалить оружие: " + type);
            return "redirect:/weapons";
        }
        return "redirect:/weapons";
    }
}