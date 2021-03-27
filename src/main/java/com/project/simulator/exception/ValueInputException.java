package com.project.simulator.exception;

public class ValueInputException extends RuntimeException {
   
	public ValueInputException(String message) {
        super(message);
    }

    public ValueInputException(String message, Throwable cause) {
        super(message, cause);
    }
}