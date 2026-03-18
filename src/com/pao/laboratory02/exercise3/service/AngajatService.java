package com.pao.laboratory02.exercise3.service;

import com.pao.laboratory02.exercise3.model.Angajat;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Completează cele 3 metode.
 * Folosește ArrayList — nu mai e nevoie de redimensionare manuală.
 */
public class AngajatService {
    private List<Angajat> angajati;

    public AngajatService() {
        this.angajati = new ArrayList<>();
    }
    /** TODO: angajati.add(a); println("Angajat adăugat: " + a.getName()); */
    public void addAngajat(Angajat a) {
        // TODO
        angajati.add(a);
        System.out.println("Angajat adăugat: " + a.getName());
    }

    /** TODO: dacă goală → mesaj; altfel parcurge cu index și afișează (i+1) + ". " + angajat */
    public void listAll() {
        // TODO
        if(angajati.size() == 0){
            System.out.println("Lista goala");
        }
        for(int i = 0; i < angajati.size(); ++i){
            System.out.println((i+1) + ". " + angajati.get(i).getName());
        }
    }

    /** TODO: parcurge lista, sumează a.salariuTotal(), returnează totalul. */
    public double totalSalarii() {
        double suma = 0;
        if(angajati.size() == 0){
            System.out.println("Lista goala");
            return 0;
        }
        for(int i = 0; i < angajati.size(); ++i){
            Angajat a = angajati.get(i);
            suma = suma + a.salariuTotal();
        }
        return suma;
    }
}
