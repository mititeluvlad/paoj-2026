package com.pao.laboratory03.exercise;
class InvalidStudentException extends RuntimeException{
    private final String message;
    InvalidStudentException(String message){
        super("Exceptie: " + message);
        this.message = message; 
    }
}