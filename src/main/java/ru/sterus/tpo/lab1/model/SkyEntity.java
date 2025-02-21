package ru.sterus.tpo.lab1.model;

abstract class SkyEntity implements SkyObject {
    protected MyColor color;

    public SkyEntity(MyColor color) {
        this.color = color;
    }

    @Override
    public void move() {
        System.out.println("Объект цвета " + color + " летит по небу.");
    }

    @Override
    public void disappear() {
        System.out.println("Объект исчезает вдали.");
    }
}
