package com.example.demo;

public class CustomWeapon implements Weapon {
    private final String type;
    private final int damage;
    private final String material;

    public CustomWeapon(String type, int damage, String material) {
        this.type = type;
        this.damage = damage;
        this.material = material;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public String getMaterial() {
        return material;
    }
}