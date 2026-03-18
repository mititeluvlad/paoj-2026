package com.pao.laboratory03.exceptions;
class DuplicateEntryException extends RuntimeException{
    private final String message;
    DuplicateEntryException(String message){
        super("Exceptie: " + message);
        this.message = message; 
    }
}