package com.pao.proiect.PlatformaLicitatii.model;

public class Client extends Utilizator{
    private double buget;

    public Client(int id,String nume, String prenume, String email, String username, double buget){
        super(id, nume, prenume, email, username);
        this.buget = buget;
    }

    @Override
    public Rol getRol(){
        return Rol.CLIENT;
    }

    public double getBuget(){return buget;}

    public void setBuget(double buget){
        this.buget = buget;
    }

    public void afiseazaSold() {
        System.out.println("Sold curent: " + buget);
    }   

    @Override
    public String toString() {
        return super.toString() + ", buget=" + buget;
    }
}