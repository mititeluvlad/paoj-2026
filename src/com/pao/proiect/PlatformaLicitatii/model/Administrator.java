package com.pao.proiect.PlatformaLicitatii.model;

public class Administrator extends Utilizator{
    private String departament;

    public Administrator(int id,String nume, String prenume, String email, String username, String departament){
        super(id, nume, prenume, email, username);
        this.departament = departament;
    }

    @Override
    public Rol getRol(){
        return Rol.ADMIN;
    }

    public String getDepartament(){return departament;}

    public void setDepartament(String dep){
        departament = dep;
    }

    @Override
    public String toString(){
        return  super.toString() + ", departament = " + departament;
    }
}