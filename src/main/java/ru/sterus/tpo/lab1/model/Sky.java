package ru.sterus.tpo.lab1.model;

import ru.sterus.tpo.lab1.model.exception.EmptyListException;

import java.util.List;

public class Sky {
    private List<FlyingEntity> flyingEntities;
    public Sky(List<FlyingEntity> flyingEntities) {
        this.flyingEntities = flyingEntities;
    }
    public void moveOne() throws EmptyListException {
        if(flyingEntities.isEmpty()){
            throw new EmptyListException();
        }
        System.out.println("Нечто двигается по небу:");
        flyingEntities.get(0).move();
        flyingEntities.get(0).disappear();
        flyingEntities.remove(0);
    }
    public void moveAll(){
        System.out.println("Все множество этих нечт двигается по небу: ");
        while (!flyingEntities.isEmpty()){
            flyingEntities.get(0).move();
            flyingEntities.get(0).disappear();
            flyingEntities.remove(0);
        }

    }
    public void addEntity(FlyingEntity flyingEntity){
        flyingEntities.add(flyingEntity);
    }
}