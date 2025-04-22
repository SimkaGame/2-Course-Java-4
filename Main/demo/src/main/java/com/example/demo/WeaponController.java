package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WeaponController {
    private final List<Weapon> weapons;

    @Autowired
    public WeaponController(List<Weapon> initialWeapons) {
        this.weapons = new ArrayList<>(initialWeapons); // Копируем начальные бины в ArrayList
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

    // Новый эндпоинт для формы добавления
    @GetMapping("/add-weapon")
    public String showAddWeaponForm(Model model) {
        return "add-weapon";
    }

    // Новый эндпоинт для обработки формы
    @PostMapping("/add-weapon")
    public String addWeapon(
            @RequestParam("type") String type,
            @RequestParam("damage") int damage,
            @RequestParam("material") String material,
            Model model) {
        // Простая валидация
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

        // Проверка на уникальность типа
        for (Weapon weapon : weapons) {
            if (weapon.getType().equalsIgnoreCase(type)) {
                model.addAttribute("errorMessage", "Оружие с типом '" + type + "' уже существует");
                return "add-weapon";
            }
        }

        // Добавление нового оружия
        Weapon newWeapon = new CustomWeapon(type, damage, material);
        weapons.add(newWeapon);

        return "redirect:/weapons"; // Перенаправление на список оружий
    }
}