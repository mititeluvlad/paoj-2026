package com.pao.proiect.PlatformaLicitatii.model;

import java.time.LocalDateTime;

public final class Tranzactie{
    private final int id;
    private final Licitatie licitatie;
    private final Oferta ofertaCastigatoare;
    private final LocalDateTime data;

    public Tranzactie(int id, Licitatie licitatie, Oferta oferta){
        this.id = id;
        this.licitatie = licitatie;
        this.ofertaCastigatoare = oferta;
        this.data = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public Licitatie getLicitatie() {
        return licitatie;
    }

    public Oferta getOfertaCastigatoare() {
        return ofertaCastigatoare;
    }

    public LocalDateTime getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "id=" + id +
                ", produs=" + licitatie.getProdus().getNume() +
                ", castigator=" + ofertaCastigatoare.getClient().getNume() +
                ", suma=" + ofertaCastigatoare.getSuma() +
                ", data=" + data +
                '}';
    }

}