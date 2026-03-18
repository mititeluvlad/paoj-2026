package com.pao.laboratory03.exceptions;
class InvalidAgeException extends RuntimeException{
    private final String message;
    InvalidAgeException(String message){
        super("Exceptie: " + message);
        this.message = message; 
    }
}