package com.example.demo;

public class Bow extends AbstractWeapon {
    public Bow() {
        this.material = "Древесина";
        this.damage = 25;
    }

    @Override
    public String getType() {
        return "Лук";
    }
}