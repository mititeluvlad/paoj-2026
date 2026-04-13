package com.pao.laboratory07.exercise3;

public final class ComandaRedusa extends Comanda {
    private int discountProcent;
    public ComandaRedusa(String nume, double pret, int ds, String client){
        super(nume,pret,client);
        this.discountProcent = ds;
    }

    @Override
    public double pretFinal(){
        return pret * (1 - discountProcent / 100.0);
    }

    @Override
    public String descriere() {
        return String.format("DISCOUNTED: %s, pret: %.2f lei (-%d%%) [%s]",
                nume, pretFinal(), discountProcent, stare);
    }

    public int getDiscountProcent() {
        return discountProcent;
    }
}
