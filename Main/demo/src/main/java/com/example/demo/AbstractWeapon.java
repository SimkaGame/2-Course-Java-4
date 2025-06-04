package com.example.demo;

public abstract class AbstractWeapon implements Weapon {
    protected String material;
    protected int damage;

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public String getMaterial() {
        return material;
    }

    @Override
    public int getDamage() {
        return damage;
    }
}