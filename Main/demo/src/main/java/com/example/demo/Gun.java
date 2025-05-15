package com.example.demo;

public class Gun extends AbstractWeapon {
    public Gun() {
        this.material = "Металл";
        this.damage = 100;
    }

    @Override
    public String getType() {
        return "Огнестрельное оружие";
    }
}