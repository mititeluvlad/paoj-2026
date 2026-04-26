package com.pao.proiect.PlatformaLicitatii.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pao.proiect.PlatformaLicitatii.model.Administrator;
import com.pao.proiect.PlatformaLicitatii.model.Client;
import com.pao.proiect.PlatformaLicitatii.model.Utilizator;

public class UtilizatorService{
    private  Map<Integer, Utilizator> utilizatori;
    private int nextId = 1;

    private UtilizatorService(){
        utilizatori = new HashMap<>();
    }

    private static class Holder{
        private static final UtilizatorService INSTANCE = new UtilizatorService();
    }

    public static UtilizatorService getInstance(){
        return Holder.INSTANCE;
    }

    public void add(Utilizator u){
        if (u == null) {
            throw new IllegalArgumentException("Utilizator null!");
        }

        if (u.getUsername() == null || u.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username invalid!");
        }

        if (u.getEmail() == null || !u.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email invalid!");
        }
        u.setId(nextId++);
        utilizatori.put(u.getId(), u);
    }

    public void delete(int id){
        utilizatori.remove(id);
    }

    public Utilizator getById(int id){
        Utilizator u = utilizatori.get(id);
        if (u == null) {
            throw new IllegalArgumentException("Utilizator inexistent!");
    }
        return utilizatori.get(id);
    }

    public List<Utilizator> getByPrenume(String prenume){
        List<Utilizator> rez = new ArrayList<>();
        for(Utilizator u : utilizatori.values()){
            if(u.getPrenume().equals(prenume)){
                rez.add(u);
            }
        }
        return rez;
    }

    public List<Utilizator> getAll() {
        return new ArrayList<>(utilizatori.values());
    }

    public List<Administrator> getAdminDupaDep(String nume){
        List<Administrator> lista = new ArrayList<>();

        for(Utilizator u : utilizatori.values()){
            if(u instanceof Administrator){
                Administrator a = (Administrator) u;
                if(a.getDepartament().equals(nume))
                    lista.add(a);
            }
        }

        lista.sort(Comparator.comparing(Administrator::getNume).thenComparing(Administrator::getPrenume));

        return lista;
    } 

    public List<Client> getClientiSortati(){
        List<Client> lista = new ArrayList<>();
        for(Utilizator u : utilizatori.values()){
            if(u instanceof Client){
                Client c = (Client) u;
                lista.add(c);
            }
        }
        lista.sort(Comparator.comparing(Client::getUsername));
        return lista;
    }
}