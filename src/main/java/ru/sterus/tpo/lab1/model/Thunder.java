package ru.sterus.tpo.lab1.model;

import java.util.Random;

public class Thunder implements SoundEmitter{
    private final int volume;
    public static final int MIN_VOLUME = 100;
    public Thunder(){
        this.volume = new Random().nextInt(50) + MIN_VOLUME;
    }

    @Override
    public void makeSound() {
        System.out.println("Гром разрывает воздух с громкостью: " + this.volume);
    }

    public int getVolume() {
        return volume;
    }
}
