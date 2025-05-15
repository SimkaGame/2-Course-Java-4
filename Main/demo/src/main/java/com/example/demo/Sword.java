package com.example.demo;

public class Sword extends AbstractWeapon {
    public Sword() {
        this.material = "Сталь";
        this.damage = 50;
    }

    @Override
    public String getType() {
        return "Меч";
    }
}