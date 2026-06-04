package com.pao.proiect.PlatformaLicitatii.service;

import com.pao.proiect.PlatformaLicitatii.repository.OfertaRepository;

public class OfertaService {

    private OfertaRepository repository =
            new OfertaRepository();

    private OfertaService() {}

    private static class Holder {
        private static final OfertaService INSTANCE =
                new OfertaService();
    }

    public static OfertaService getInstance() {
        return Holder.INSTANCE;
    }

    public void insertOferta(int clientId,
                             int licitatieId,
                             double suma) {

        repository.insertOferta(
                clientId,
                licitatieId,
                suma
        );

        System.out.println("Oferta salvata in DB!");
    }

    public void updateStatusOferta(int ofertaId,
                                   String status) {

        repository.updateStatus(ofertaId, status);
    }

    public void raportTopClientiDupaOferte() {

        AuditService.getInstance().logActiune("respingere_top_clienti");

        repository.raportTopClientiDupaOferte();
    }

    public void raportOferteComplete() {
        AuditService.getInstance().logActiune("raport_oferte");

        repository.raportOferteComplete();
    }
}