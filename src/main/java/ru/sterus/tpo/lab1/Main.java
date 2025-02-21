package ru.sterus.tpo.lab1;


import ru.sterus.tpo.lab1.model.FlyingEntity;
import ru.sterus.tpo.lab1.model.MyColor;
import ru.sterus.tpo.lab1.model.Sky;
import ru.sterus.tpo.lab1.model.Thunder;
import ru.sterus.tpo.lab1.model.exception.EmptyListException;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
    private static FlyingEntity generateOne(){
        return new FlyingEntity(randomEnum(MyColor.class), new Thunder());
    }
    public static void main(String[] args) {
        List<FlyingEntity> entities = Stream.generate(Main::generateOne).limit(new Random().nextInt(10) + 1).collect(Collectors.toList());
        Sky sky = new Sky(entities);
        try {
            sky.moveOne();
            sky.moveOne();
        } catch (EmptyListException e) {
            System.out.println("Но никто не пришел");
        }
        sky.moveAll();

    }
}