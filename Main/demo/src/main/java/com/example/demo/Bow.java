package com.example.demo;

import org.springframework.stereotype.Component;

@Component("bow")
public class Bow extends AbstractWeapon {
    public Bow() {
        this.material = "Древесина";
        this.damage = 25;
    }

    @Override
    public String getType() {
        return "Лук";
    }

    public void initialize() {
    }

    public void cleanup() {
    }
}