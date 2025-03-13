package com.example.demo;

public class WeaponDisplay<T extends Weapon> {
    private T weapon;

    public WeaponDisplay(T weapon) {
        this.weapon = weapon;
    }

    public void display() {
        System.out.println("Тип оружия: " + weapon.getType());
    }
}