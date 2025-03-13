package com.example.demo;

public class Sword implements Weapon {
    private String material;
    private int damage;

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

    public void initialize() {
        System.out.println("Sword initialized with material: " + material + " and damage: " + damage);
    }

    public void cleanup() {
        System.out.println("Sword is being destroyed");
    }

    @Override
    public String getType() {
        return "Меч";
    }
}
