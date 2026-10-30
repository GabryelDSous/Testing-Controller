package dev.gabryel.testingcontroller.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException() {
        super("Please, enter a valid name");
    }
}
