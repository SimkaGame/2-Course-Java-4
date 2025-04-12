package com.example.demo;

import org.springframework.stereotype.Component;

@Component("gun")
public class Gun extends AbstractWeapon {
    public Gun() {
        this.material = "Металл";
        this.damage = 100;
    }

    @Override
    public String getType() {
        return "Огнестрельное оружие";
    }

    public void initialize() {
    }

    public void cleanup() {
    }
}