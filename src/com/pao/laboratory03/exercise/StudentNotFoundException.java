package com.pao.laboratory03.exercise;
class StudentNotFoundException extends RuntimeException{
    private final String message;
    StudentNotFoundException(String message){
        super("Exceptie: " + message);
        this.message = message; 
    }
}