package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/fight")
    public String fight(
            @RequestParam("num1") double enemyHp,
            @RequestParam("num2") double enemyArmor,
            @RequestParam("operation") String weaponChoice,
            Model model) {
        try {
            // Находим выбранное оружие
            Weapon selectedWeapon = null;
            for (Weapon weapon : weapons) {
                if (weapon.getType().equalsIgnoreCase(weaponChoice)) {
                    selectedWeapon = weapon;
                    break;
                }
            }
            if (selectedWeapon == null) {
                model.addAttribute("errorMessage", "Недопустимое оружие: " + weaponChoice);
                return "fight-form"; // Возвращаем форму с ошибкой
            }

            // Расчет боя
            double weaponDamage = ((AbstractWeapon) selectedWeapon).getDamage();
            double effectiveDamage = Math.max(0, weaponDamage - enemyArmor); // Урон после учета защиты
            double remainingHp = enemyHp - effectiveDamage;

            // Определяем результат
            boolean isEnemyDefeated = remainingHp <= 0;
            String fightResult = isEnemyDefeated
                    ? "Победа! Враг повержен!"
                    : "Враг выжил! У него осталось " + String.format("%.2f", remainingHp) + " здоровья.";

            // Передаем данные в шаблон
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
            return "fight-form"; // Возвращаем форму с ошибкой
        }
    }

    @GetMapping("/fight-form")
    public String showFightForm(Model model) {
        model.addAttribute("weapons", weapons);
        return "fight-form";
    }
}