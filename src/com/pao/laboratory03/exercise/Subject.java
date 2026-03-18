package com.pao.laboratory03.exercise;

public enum Subject{

    PAOJ("Programare Avansata pe Obiecte", 10),
    BD("Baze de Date", 1),
    SO("Sisteme de Operare", 15),
    RC("Retele de Calculatoare", 10);

    public final String fullName;
    public final int credits;

    private Subject(String fullName, int credits){ 
        this.fullName = fullName; 
        this.credits = credits; 
    }

    public String getFullName(){ return fullName; }
    public int getCredits(){ return credits; }

    @Override
    public String toString(){
        return name() + "( " + getFullName() + ", " + getCredits() + " )";
    }
}