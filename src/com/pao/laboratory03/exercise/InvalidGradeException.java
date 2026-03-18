package com.pao.laboratory03.exercise;
class InvalidGradeException extends RuntimeException{
    private final String message;
    InvalidGradeException(String message){
        super("Exceptie: " + message);
        this.message = message; 
    }
}