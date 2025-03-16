package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeaponDisplay {
    private List<Weapon> weapons;

    @Autowired
    public WeaponDisplay(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public void displayAll() {
        weapons.forEach(weapon -> {
            if (weapon instanceof Sword) {
                Sword sword = (Sword) weapon;
                System.out.println("Тип оружия: " + sword.getType() +
                        ", Материал: " + sword.getMaterial() +
                        ", Урон: " + sword.getDamage());
            } else if (weapon instanceof Gun) {
                Gun gun = (Gun) weapon;
                System.out.println("Тип оружия: " + gun.getType() +
                        ", Материал: " + gun.getMaterial() +
                        ", Урон: " + gun.getDamage());
            } else if (weapon instanceof Bow) {
                Bow bow = (Bow) weapon;
                System.out.println("Тип оружия: " + bow.getType() +
                        ", Материал: " + bow.getMaterial() +
                        ", Урон: " + bow.getDamage());
            }
        });
    }
}
