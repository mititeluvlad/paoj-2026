package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends PersoanaFizica{
    private boolean bonus;

    public CIMColaborator(){}
    

    @Override
    public TipColaborator getTip(){
        return TipColaborator.CIM;
    }

    @Override
    public void citeste(Scanner in) {
        nume = in.next();
        prenume = in.next();
        venit = in.nextDouble();
        String ok = in.next();
        if(ok.equalsIgnoreCase("DA")){
            bonus = true;
        }
        else 
            bonus = false;
    }

    @Override 
    public boolean areBonus(){
        if(bonus == true)
            return true;
        else return false;
        }

    @Override
    public void afiseaza() {
        System.out.printf("CIM: %s %s, venit net anual: %.2f lei\n",
                nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "CIM";
    }

    @Override
    public double calculeazaVenitNetAnual(){
        double suma = venit * 12 * 0.55;
        if(bonus == true) suma = 1.10 * suma;
        return suma;
    }
}