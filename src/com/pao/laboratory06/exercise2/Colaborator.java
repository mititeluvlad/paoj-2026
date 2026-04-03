package com.pao.laboratory06.exercise2;

abstract class Colaborator implements IOperatiiCitireScriere{
    protected String nume;
    protected String prenume;
    protected double venit;

    Colaborator(){}


    public abstract double calculeazaVenitNetAnual();
    public abstract TipColaborator getTip();

    public String getNume(){return nume;}
    public String getPrenume(){return prenume;}
    public double getVenit(){return venit;}
}