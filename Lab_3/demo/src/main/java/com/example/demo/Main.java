package com.example.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        WeaponDisplay<?> swordDisplay = (WeaponDisplay<?>) context.getBean("swordDisplay");
        swordDisplay.display();

        WeaponDisplay<?> gunDisplay = (WeaponDisplay<?>) context.getBean("gunDisplay");
        gunDisplay.display();

        WeaponDisplay<?> bowDisplay = (WeaponDisplay<?>) context.getBean("bowDisplay");
        bowDisplay.display();
    }
}
