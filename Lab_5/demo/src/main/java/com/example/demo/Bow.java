package com.example.demo;

import org.springframework.stereotype.Component;

@Component("bow")
public class Bow implements Weapon {
    private String material = "Древесина";
    private int damage = 25;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public String getType() {
        return "Лук";
    }
}
