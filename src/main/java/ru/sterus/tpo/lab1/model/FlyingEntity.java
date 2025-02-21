package ru.sterus.tpo.lab1.model;

public class FlyingEntity extends SkyEntity implements SoundEmitter{
    private final Thunder thunder;
    private final int currentId;
    private static int id = 1;
    public FlyingEntity(MyColor color, Thunder thunder) {
        super(color);
        this.thunder = thunder;
        this.currentId = id++;
    }

    @Override
    public void makeSound() {
        thunder.makeSound();
    }

    @Override
    public void move() {
        super.move();
        System.out.println("Его id = " + currentId);
        makeSound();
    }

    @Override
    public void disappear() {
        System.out.println("Объект с id = " + currentId + " исчезает");
        super.disappear();
    }

    public Thunder getThunder() {
        return thunder;
    }

}