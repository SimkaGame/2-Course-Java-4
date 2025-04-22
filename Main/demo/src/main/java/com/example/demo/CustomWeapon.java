package com.example.demo;

public class CustomWeapon extends AbstractWeapon {
    private final String type;

    public CustomWeapon(String type, int damage, String material) {
        this.type = type;
        this.damage = damage;
        this.material = material;
    }

    @Override
    public String getType() {
        return type;
    }
}