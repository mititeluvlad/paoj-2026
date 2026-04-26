package com.pao.proiect.PlatformaLicitatii.model;

import java.util.Objects;

public class Produs{
    private int id;
    private String nume;
    private double pretStart;
    private CategorieProdus categorie;

    public Produs(int id, String nume, double pretStart, CategorieProdus categorie){
        this.id = id;
        this.nume = nume;
        this.pretStart = pretStart;
        this.categorie = categorie;
    }

        public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public double getPretStart() {
        return pretStart;
    }

    public CategorieProdus getCategorie() {
        return categorie;
    }

    public void setPretStart(double pretStart) {
        this.pretStart = pretStart;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", pretStart=" + pretStart +
                ", categorie=" + categorie +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produs prod = (Produs) o;
        return Objects.equals(id, prod.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}