package com.pao.proiect.PlatformaLicitatii.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pao.proiect.PlatformaLicitatii.model.CategorieProdus;
import com.pao.proiect.PlatformaLicitatii.model.Client;
import com.pao.proiect.PlatformaLicitatii.model.Licitatie;
import com.pao.proiect.PlatformaLicitatii.model.Oferta;
import com.pao.proiect.PlatformaLicitatii.model.Produs;
import com.pao.proiect.PlatformaLicitatii.model.Tranzactie;


public class LicitatieService{
    private Map<Integer, Licitatie> licitatii;
    private List<Tranzactie> tranzactii;
    private int nextId = 1;
    private int nextProdusId = 1;
    private UtilizatorService utilizatorService = UtilizatorService.getInstance();

    private LicitatieService(){
        licitatii = new HashMap<>();
        tranzactii = new ArrayList<>();
    }

    private static class Holder{
        private static final LicitatieService INSTANCE = new LicitatieService();
    }

    public static LicitatieService getInstance(){
        return Holder.INSTANCE;
    }

    public void adauga(Licitatie l){
        if (l == null || l.getProdus() == null) {
            throw new IllegalArgumentException("Licitatie sau produs null!");
        }

        if (l.getProdus().getPretStart() <= 0) {
            throw new IllegalArgumentException("Pret invalid!");
        }
        l.setId(nextId++);
        licitatii.put(l.getId(), l);
    }

    public void sterge(int id){
        licitatii.remove(id);
    }

    public Licitatie getById(int id){
        Licitatie l = licitatii.get(id);
        if (l == null) {
            throw new IllegalArgumentException("Licitatie inexistenta!");
        }
        return licitatii.get(id);
    }

    public Produs creeazaProdus(String nume, double pret, CategorieProdus categorie) {
        return new Produs(nextProdusId++, nume, pret, categorie);
    }

    public List<Licitatie> getByNumeProdus(String nume){
        List<Licitatie> rez = new ArrayList<>();

        for(Licitatie l : licitatii.values()){
            if(l.getProdus().getNume().equals(nume)){
                rez.add(l);
            }
        }
        return rez;
    }

    public List<Licitatie> getAll() {
        return new ArrayList<>(licitatii.values());
    }

    public void aprobaOferta(int licitatieId, int ofertaId){
        Licitatie l = getById(licitatieId);
        l.aprobareOferta(ofertaId);
    }

    public void respingeOferta(int licitatieId, int ofertaId){
    Licitatie l = getById(licitatieId);
    l.respingeOferta(ofertaId);
}

    public void inchideLicitatie(int id){
        Licitatie l = getById(id);
        l.inchideLicitatie();

        try {
            Oferta castigatoare = l.getOfertaCastigatoare();
            Tranzactie t = new Tranzactie(tranzactii.size() + 1, l, castigatoare);
            tranzactii.add(t);
            //scadem sold ul clientului
            Client c = (Client) utilizatorService.getById(castigatoare.getClient().getId());
            c.setBuget(c.getBuget() - castigatoare.getSuma());

            l.inchideLicitatie();
        } 
        catch (Exception e) {
            System.out.println("Nu s-a putut tranzactiona: " + e.getMessage());
        }
    }

    public void afiseazaTranzactii(){
        for(Tranzactie t : tranzactii){
            System.out.println(t);
        }
    }

    public void afiseazaOferteSortate(int licitatieId){
        Licitatie l = getById(licitatieId);

        List<Oferta> lista = new ArrayList<>(l.getOferteAprobate());
        Collections.sort(lista);

        for(Oferta o : lista){
            System.out.println(o);
        }
    }

    public void afiseazaTranzactiiSortate(){
        List<Tranzactie> lista = new ArrayList<>(tranzactii);
        lista.sort(Comparator.comparing(Tranzactie::getData));

        for(Tranzactie t : lista){
            System.out.println(t);
        }
    }

    public List<Licitatie> getLicitatiiSortateDupaPret() {
        List<Licitatie> lista = new ArrayList<>(licitatii.values());
        
        lista.sort(Comparator.comparing(l -> l.getProdus().getPretStart()));

        return lista;
    }
    
}