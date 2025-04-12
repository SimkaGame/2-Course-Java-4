package com.example.demo;

import org.springframework.stereotype.Component;

@Component("sword")
public class Sword extends AbstractWeapon {
    public Sword() {
        this.material = "Сталь";
        this.damage = 50;
    }

    @Override
    public String getType() {
        return "Меч";
    }

    // Добавлено для совместимости с beans.xml (можно убрать, если XML не используется)
    public void initialize() {
    }

    public void cleanup() {
    }
}