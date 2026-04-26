package com.pao.proiect.PlatformaLicitatii.model;
import com.pao.proiect.PlatformaLicitatii.exception.LicitatieInchisaException;
import com.pao.proiect.PlatformaLicitatii.exception.NuExistaOferteAprobateException;
import com.pao.proiect.PlatformaLicitatii.exception.OfertaInexistentaException;
import com.pao.proiect.PlatformaLicitatii.exception.OfertaPreaMicaException;

import java.util.ArrayList;
import java.util.List;

public class Licitatie{
    private int id;
    private Produs produs;
    private List<Oferta> oferteInAsteptare;
    private List<Oferta> oferteAprobate;
    private StatusLicitatie status;
    private int nextOfertaId = 1;

    public Licitatie(int id, Produs produs, StatusLicitatie status){
        this.id = id;
        this.produs = produs;
        this.status = status;
        this.oferteInAsteptare = new ArrayList<>();
        this.oferteAprobate = new ArrayList<>();
    }

    public int getId(){return id;}
    public Produs getProdus(){return produs;}
    public StatusLicitatie getStatus(){return status;}

    public void setId(int id) {
        this.id = id;
    }

    public List<Oferta> getOferteInAsteptare(){return oferteInAsteptare;}
    public List<Oferta> getOferteAprobate(){return oferteAprobate;}

    public void adaugaOferta(Oferta oferta){
        if (oferta == null || oferta.getClient() == null) {
            throw new IllegalArgumentException("Oferta invalida!");
        }

        if (oferta.getSuma() <= 0) {
            throw new IllegalArgumentException("Suma invalida!");
        }

        if (status == StatusLicitatie.INCHISA) {
            throw new LicitatieInchisaException("Licitatie inchisa!");
        }

        if (oferta.getSuma() < produs.getPretStart()) {
            throw new OfertaPreaMicaException("Oferta prea mica!");
        }
        oferta.setId(nextOfertaId++);
        oferteInAsteptare.add(oferta);
    }

    public void respingeOferta(int ofertaId){
        boolean ok = false;
        int poz = 0;
        for(Oferta o : oferteInAsteptare){
            if(o.getId() == ofertaId){
                o.respinge();
                ok = true;
                break;
            }
            poz += 1;
        }
        if(ok == false)
            throw new OfertaInexistentaException("Oferta nu exista!");
        else
            oferteInAsteptare.remove(poz);
    }

    public void aprobareOferta(int ofertaId){
        boolean ok = false;
        int poz = 0;
        for(Oferta o : oferteInAsteptare){
            if(o.getId() == ofertaId){
                o.aproba();
                oferteAprobate.add(o);
                ok = true;
                break;
            }
            poz += 1;
        }
        if(ok == false)
            throw new OfertaInexistentaException("Oferta nu exista!");
        
        oferteInAsteptare.remove(poz);
        
    }

    public void inchideLicitatie(){
        this.status = StatusLicitatie.INCHISA;
    }

    public Oferta getOfertaCastigatoare(){
        if(oferteAprobate.isEmpty())
            throw new NuExistaOferteAprobateException("Nu exista oferte aprobate!");
        Oferta oferta = oferteAprobate.get(0);
        double maxi = oferta.getSuma();
        for(int i = 1; i < oferteAprobate.size(); ++i){
            if(oferteAprobate.get(i).getSuma() > maxi){
                maxi = oferteAprobate.get(i).getSuma();
                oferta = oferteAprobate.get(i);
            }
        }
        return oferta;
    }

    public void afisareDetaliata() {
        System.out.println("=== LICITATIE ===");
        System.out.println("Produs: " + produs.getNume());
        System.out.println("Status licitatie: " + status);

        System.out.println("\n--- Oferte PENDING ---");
        for (Oferta o : oferteInAsteptare) {
            if (o.getStatus() == StatusOferta.PENDING) {
                System.out.println(o);
            }
        }

        System.out.println("\n--- Oferte ACCEPTATE ---");
        for (Oferta o : oferteAprobate) {
            if (o.getStatus() == StatusOferta.ACCEPTATA) {
                System.out.println(o);
            }
        }

        System.out.println("\n--- Oferte RESPINSE ---");
        for (Oferta o : oferteInAsteptare) {
            if (o.getStatus() == StatusOferta.RESPINSA) {
                System.out.println(o);
            }
        }
    }
    @Override
    public String toString() {
        return id + " | " + produs.getNume() + " | " + status;
    }
}