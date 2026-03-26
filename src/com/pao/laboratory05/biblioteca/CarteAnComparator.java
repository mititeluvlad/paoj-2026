package com.pao.laboratory05.biblioteca;

import java.util.Comparator;

public class CarteAnComparator implements Comparator<Carte> {
    @Override
    public int compare(Carte c1, Carte c2){
        if(c1.getAn() < c2.getAn()){
            return -1;
        }
        else if(c1.getAn() > c2.getAn()){
            return 1;
        }
        else 
            return 0;
    }
}