package com.example.demo;

import org.springframework.stereotype.Component;

@Component("sword")
public class Sword implements Weapon {
    private String material = "Сталь";
    private int damage = 50;

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
        return "Меч";
    }
}
