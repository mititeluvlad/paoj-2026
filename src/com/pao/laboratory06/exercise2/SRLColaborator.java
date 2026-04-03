package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends PersoanaJuridica{

    private double CheltuieliLunare;
    public SRLColaborator(){}

    @Override
    public TipColaborator getTip(){
        return TipColaborator.SRL;
    }

     @Override
    public void citeste(Scanner in) {
        nume = in.next();
        prenume = in.next();
        venit = in.nextDouble();
        CheltuieliLunare = in.nextDouble();
    }

    @Override
    public void afiseaza() {
        System.out.printf("SRL: %s %s, venit net anual: %.2f lei\n",
            nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "SRL";
    }

    @Override
    public double calculeazaVenitNetAnual(){
       return (venit - CheltuieliLunare) * 12 * 0.84;
    }
}