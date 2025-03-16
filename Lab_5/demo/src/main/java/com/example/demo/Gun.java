package com.example.demo;

import org.springframework.stereotype.Component;

@Component("gun")
public class Gun implements Weapon {
    private String material = "Металл";
    private int damage = 100;

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
        return "Огнестрельное оружие";
    }
}
