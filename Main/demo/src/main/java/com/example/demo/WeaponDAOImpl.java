package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WeaponDAOImpl implements WeaponDAO {
    private static final Logger logger = LoggerFactory.getLogger(WeaponDAOImpl.class);
    private final List<Weapon> weapons;

    public WeaponDAOImpl() {
        logger.debug("Инициализация WeaponDAOImpl с начальными данными");
        this.weapons = new ArrayList<>();
        weapons.add(new CustomWeapon("Меч", 50, "Сталь"));
        weapons.add(new CustomWeapon("Огнестрельное оружие", 100, "Металл"));
        weapons.add(new CustomWeapon("Лук", 25, "Дерево"));
    }

    @Override
    public List<Weapon> findAll() {
        logger.debug("Получение всех оружий, размер списка: {}", weapons.size());
        return new ArrayList<>(weapons);
    }

    @Override
    public Weapon findByType(String type) {
        logger.debug("Поиск оружия по типу: {}", type);
        Weapon weapon = weapons.stream()
                .filter(w -> w.getType().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
        logger.debug("Найдено оружие: {}", weapon);
        return weapon;
    }

    @Override
    public void save(Weapon weapon) {
        logger.debug("Сохранение оружия: {}", weapon.getType());
        weapons.add(weapon);
    }

    @Override
    public void update(String type, Weapon updatedWeapon) {
        logger.debug("Обновление оружия с типом: {}", type);
        for (int i = 0; i < weapons.size(); i++) {
            if (weapons.get(i).getType().equalsIgnoreCase(type)) {
                weapons.set(i, updatedWeapon);
                logger.debug("Оружие обновлено: {}", updatedWeapon.getType());
                return;
            }
        }
        logger.warn("Оружие с типом {} не найдено для обновления", type);
    }

    @Override
    public void delete(String type) {
        logger.debug("Удаление оружия с типом: {}", type);
        boolean removed = weapons.removeIf(w -> w.getType().equalsIgnoreCase(type));
        if (!removed) {
            logger.warn("Оружие с типом {} не найдено для удаления", type);
        }
    }
}