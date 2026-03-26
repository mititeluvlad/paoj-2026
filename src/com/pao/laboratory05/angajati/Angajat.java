package com.pao.laboratory05.angajati;

public class Angajat implements Comparable<Angajat>{
    private String nume;
    private Departament departament;
    private double salariu;

    public Angajat(String nume, Departament dep, double sal){
        this.nume = nume;
        this.departament = dep;
        this.salariu = sal;
    }

    public String getNume(){return nume;}
    public Departament getDepartament(){return departament;}
    public double getSalariu(){return salariu;}

    @Override
    public String toString(){
        return "Angajat{nume=" + nume + ",departament=Departament[nume=" + departament.nume() + ", locatie=" + departament.locatie() + "], salriu=" + salariu + "}"; 
    }

    @Override
    public int compareTo(Angajat j){
        return Double.compare(j.salariu,this.salariu);
    }
}