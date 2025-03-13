package com.example.demo;

public class Gun implements Weapon {
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
        System.out.println("Gun initialized with material: " + material + " and damage: " + damage);
    }

    public void cleanup() {
        System.out.println("Gun is being destroyed");
    }

    @Override
    public String getType() {
        return "Огнестрельное оружие";
    }
}
