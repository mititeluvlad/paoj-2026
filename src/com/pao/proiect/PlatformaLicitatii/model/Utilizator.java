package com.pao.proiect.PlatformaLicitatii.model;
import java.util.Objects;
public abstract class Utilizator{
    protected int id;
    protected String nume;
    protected String prenume;
    protected String email;
    protected String username;

    public Utilizator(int id,String nume, String prenume, String email, String username){
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.username =username;
    }

    public abstract Rol getRol();

    public int getId(){return id;}
    public String getNume(){return nume;}
    public String getPrenume(){return prenume;}
    public String getEmail(){return email;}
    public String getUsername(){return username;}

    public void setUsername(String username){
        this.username = username;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
            "id = " + id +
            ", nume = '" + nume + '\'' +
            ", prenume = '" + prenume + '\'' +
            ", email = '" + email + '\'' +
            ", username = '" + username + '\'' +
            ", rol = '" + getRol() + '\'' +
            '}';
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizator user = (Utilizator) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}