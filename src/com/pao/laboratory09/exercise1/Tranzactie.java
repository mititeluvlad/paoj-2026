package com.pao.laboratory09.exercise1;
import java.io.Serializable;

public class Tranzactie implements Serializable{
    private int id;
    private double suma;
    private String data;
    private String ContSursa;
    private String ContDestinatie;
    private TipTranzactie tip;
    private transient String note;
    private static final long serialVersionUID = 1L;

    public Tranzactie(int id, double suma, String data, String ContSursa, String ContDestinatie,
        TipTranzactie tip){
            this.id = id;
            this.suma = suma;
            this.data = data;
            this.ContSursa = ContSursa;
            this.ContDestinatie = ContDestinatie;
            this.tip = tip;
        }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s %s: %.2f RON | %s -> %s",
                id, data, tip, suma, ContSursa, ContDestinatie);
    }
}