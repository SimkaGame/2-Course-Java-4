package com.example.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.demo");

        WeaponDisplay weaponDisplay = context.getBean(WeaponDisplay.class);
        weaponDisplay.displayAll();
    }
}
