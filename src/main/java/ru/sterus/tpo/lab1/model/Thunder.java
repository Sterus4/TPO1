package ru.sterus.tpo.lab1.model;

import java.util.Random;

public class Thunder implements SoundEmitter{
    private final int volume;
    public Thunder(){
        this.volume = new Random().nextInt(100);
    }

    @Override
    public void makeSound() {
        System.out.println("Гром разрывает воздух с громкостью: " + this.volume);
    }
}
