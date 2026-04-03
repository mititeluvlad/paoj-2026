package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica{

    private double CheltuieliLunare;
    public PFAColaborator(){}
    
    @Override
    public double calculeazaVenitNetAnual(){
       double venit_net = (venit - CheltuieliLunare) * 12;
       double impozit = venit_net * 0.1;
       double cass;
       if(venit_net < 6 * 48600)
        cass = 0.1 * 6 * 48600;
       else if(venit_net >= 6 * 48.600 && venit_net <= 72 * 48600)
        cass = 0.1 * venit_net;
       else
        cass =  0.1 * 72 * 48.600;
    
       double cas;
        if(venit_net < 12 * 48600)
        cas = 0;
       else if(venit_net >= 12 * 48.600 && venit_net <= 24 * 48600)
        cas = 0.25 * 12 * 48600;
       else
        cas =  0.25 * 24 * 48600;

       return venit_net - impozit - cass - cas;
    }
    @Override
    public TipColaborator getTip(){
        return TipColaborator.PFA;
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
        System.out.printf("PFA: %s %s, venit net anual: %.2f lei\n",
            nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "PFA";
    }
}