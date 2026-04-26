package com.pao.proiect.PlatformaLicitatii.model;

import java.time.LocalDateTime;

public class Oferta implements Comparable<Oferta>{
    private int id;
    private Client client;
    private double suma;
    private LocalDateTime data;
    private StatusOferta status;

    public Oferta(int id, Client client, double suma, StatusOferta status){
        this.id = id;
        this.client = client;
        this.suma = suma;
        this.status = status;
        data = LocalDateTime.now();
    }

    @Override
    public int compareTo(Oferta o){
        return Double.compare(o.suma, this.suma);
    }

    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public double getSuma() {
        return suma;
    }

    public LocalDateTime getData() {
        return data;
    }

    public StatusOferta getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void aproba() {
        this.status = StatusOferta.ACCEPTATA;
    }

    public void respinge() {
        this.status = StatusOferta.RESPINSA;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "id=" + id +
                ", client=" + client.getUsername() +
                ", suma=" + suma +
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}