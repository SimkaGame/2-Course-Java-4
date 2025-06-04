package com.example.demo;

import java.util.List;

public interface WeaponDAO {
    List<Weapon> findAll();
    Weapon findByType(String type);
    void save(Weapon weapon);
    void update(String type, Weapon weapon);
    void delete(String type);
}