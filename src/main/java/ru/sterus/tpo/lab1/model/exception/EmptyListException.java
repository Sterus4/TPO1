package ru.sterus.tpo.lab1.model.exception;

public class EmptyListException extends Exception{
    public EmptyListException() {
        super("List is empty");
    }
}
