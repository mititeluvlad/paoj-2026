package com.pao.laboratory08.exercise1;
public class Student implements Cloneable {
    private String nume;
    private int varsta;
    private Adresa adresa;

    // constructor(String nume, int varsta, Adresa adresa)
    // getteri, setteri
    // toString() → "Student{nume='...', varsta=..., adresa=Adresa{oras='...', strada='...'}}"
    public String getNume(){return nume;}
    public int getVarsta(){return varsta;}
    public Adresa getAdresa(){return adresa;}

    public void setNume(String nume){
        this.nume = nume;
    }
    public void setVarsta(int vr){
        varsta = vr;
    }
    public void setAdresa(Adresa adr){
        adresa = adr;
    }

    @Override
    public String toString() {
        return "Student{nume='" + nume + "', varsta=" + varsta + ", adresa=" + adresa + "}";
    }

    // clone() — implementare diferită pentru shallow vs. deep (vezi mai jos)
    // Deep clone:
    public Student(String nume, int varsta, Adresa adresa){
        this.nume = nume;
        this.varsta = varsta;
        this.adresa = adresa;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public Object deepClone() throws CloneNotSupportedException {
        Student clona = (Student) super.clone();
        clona.setAdresa((Adresa) this.adresa.clone()); // copie independentă
        return clona;
    }
}