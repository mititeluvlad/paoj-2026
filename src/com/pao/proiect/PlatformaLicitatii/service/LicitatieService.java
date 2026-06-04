package com.pao.proiect.PlatformaLicitatii.service;

import java.util.*;

import com.pao.proiect.PlatformaLicitatii.model.*;
import com.pao.proiect.PlatformaLicitatii.repository.LicitatieRepository;

public class LicitatieService {

    private Map<Integer, Licitatie> licitatii;
    private List<Tranzactie> tranzactii;

    private int nextId = 1;
    private int nextProdusId = 1;

    private UtilizatorService utilizatorService = UtilizatorService.getInstance();

    private LicitatieRepository repository = new LicitatieRepository();

    private LicitatieService() {
        licitatii = new HashMap<>();
        tranzactii = new ArrayList<>();
    }

    private static class Holder {
        private static final LicitatieService INSTANCE = new LicitatieService();
    }

    public static LicitatieService getInstance() {
        return Holder.INSTANCE;
    }

    public void adauga(Licitatie l) {

        AuditService.getInstance().logActiune("adauga_licitatie");

        if (l == null || l.getProdus() == null) {
            throw new IllegalArgumentException("Licitatie sau produs null!");
        }

        l.setId(nextId++);
        licitatii.put(l.getId(), l);

        repository.save(l);
    }

    public Licitatie getById(int id) {

        Licitatie l = licitatii.get(id);

        if (l == null)
            throw new IllegalArgumentException("Licitatie inexistenta!");

        return l;
    }

    public List<Licitatie> getAll() {
        return new ArrayList<>(licitatii.values());
    }

    public Produs creeazaProdus(String nume,
                                double pret,
                                CategorieProdus categorie) {

        return new Produs(nextProdusId++, nume, pret, categorie);
    }

    public List<Licitatie> getByNumeProdus(String nume) {

        List<Licitatie> rez = new ArrayList<>();

        for (Licitatie l : licitatii.values()) {
            if (l.getProdus().getNume().equals(nume)) {
                rez.add(l);
            }
        }

        return rez;
    }

    public void aprobaOferta(int licitatieId, int ofertaId) {

        AuditService.getInstance().logActiune("aprobare_oferta");

        Licitatie l = getById(licitatieId);

        l.aprobareOferta(ofertaId);

        repository.updateOfertaStatus(ofertaId, "ACCEPTATA");
    }

    public void respingeOferta(int licitatieId, int ofertaId) {

        AuditService.getInstance().logActiune("respingere_oferta");

        Licitatie l = getById(licitatieId);

        l.respingeOferta(ofertaId);

        repository.updateOfertaStatus(ofertaId, "RESPINSA");
    }

    public void inchideLicitatie(int id) {

        AuditService.getInstance().logActiune("inchidere_licitatie");

        Licitatie l = getById(id);

        l.inchideLicitatie();

        try {

            Oferta cast = l.getOfertaCastigatoare();

            if (cast != null) {
                repository.finalizeazaLicitatie(
                        l.getId(),
                        cast.getClient().getId(),
                        cast.getId(),
                        cast.getSuma()
                );

                Tranzactie t =
                        new Tranzactie(tranzactii.size() + 1, l, cast);

                tranzactii.add(t);
            }

        } catch (Exception e) {

            System.out.println(
                    "Eroare inchidere licitatie: " + e.getMessage()
            );
        }
    }

    public void afiseazaTranzactiiSortate() {

        AuditService.getInstance().logActiune("afiseaza_tranzactii_sortate");
        List<Tranzactie> lista =
                new ArrayList<>(tranzactii);

        lista.sort(Comparator.comparing(Tranzactie::getData));

        for (Tranzactie t : lista) {
            System.out.println(t);
        }
    }

    public List<Licitatie> getLicitatiiSortateDupaPret() {

        AuditService.getInstance().logActiune("afiseaza_licitatii_sortate");

        List<Licitatie> lista =
                new ArrayList<>(licitatii.values());

        lista.sort(
                Comparator.comparing(
                        l -> l.getProdus().getPretStart()
                )
        );

        return lista;
    }

    public void afiseazaOferteSortate(int licitatieId) {

        AuditService.getInstance().logActiune("afiseaza_oferte_sortate");

        Licitatie l = getById(licitatieId);

        List<Oferta> lista =
                new ArrayList<>(l.getOferteAprobate());

        if (lista.isEmpty()) {
            System.out.println("Nu exista oferte aprobate.");
            return;
        }

        lista.sort(Comparator.comparing(Oferta::getSuma));

        for (Oferta o : lista) {

            System.out.println(
                    o.getId() + " | " +
                    o.getClient().getUsername() + " | " +
                    o.getSuma() + " | " +
                    o.getStatus()
            );
        }
    }

    public void raportLicitatiiCuNumarOferte() {
        AuditService.getInstance().logActiune("raport_licitatii_cu_numar_oferte");
        repository.raportLicitatiiCuNumarOferte();
    }
}