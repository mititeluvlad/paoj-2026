package com.pao.laboratory05.biblioteca;

import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService{
    private Carte[] carti;

    private BibliotecaService(){
        this.carti = new Carte[0];
    }
    private static class Holder{
        private static final BibliotecaService INSTANCE = new BibliotecaService();
    }

    public static BibliotecaService getInstance(){
        return Holder.INSTANCE;
    }

    public void addCarte(Carte carte){
        int n = carti.length;
        Carte[] c = new Carte[n+1];
        for(int i = 0; i < n; ++i){
            c[i] = carti[i];
        }
        c[n] = carte;
        carti = c;
    }
    
    public void listSortedByRating(){
        int n = carti.length;
        Carte[] c = carti.clone();
        Arrays.sort(c);
        for(int i = 0; i < n; ++i){
            System.out.println(c[i].toString());
        }
    }

    public void listSortedBy(Comparator<Carte> comparator){
        int n = carti.length;
        Carte[] c = carti.clone();
        Arrays.sort(c, comparator);
        for(int i = 0; i < n; ++i){
            System.out.println(c[i].toString());
        }
    }
}