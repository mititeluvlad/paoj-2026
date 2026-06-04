package com.pao.proiect.PlatformaLicitatii.service;

import java.util.*;

import com.pao.proiect.PlatformaLicitatii.model.Administrator;
import com.pao.proiect.PlatformaLicitatii.model.Client;
import com.pao.proiect.PlatformaLicitatii.model.Utilizator;
import com.pao.proiect.PlatformaLicitatii.repository.AdministratorRepository;
import com.pao.proiect.PlatformaLicitatii.repository.ClientRepository;

public class UtilizatorService {

    private Map<Integer, Utilizator> utilizatori;

    private int nextId = 1;

    private ClientRepository clientRepository =
            new ClientRepository();

    private AdministratorRepository administratorRepository =
            new AdministratorRepository();

    private UtilizatorService() {
        utilizatori = new HashMap<>();
    }

    private static class Holder {

        private static final UtilizatorService INSTANCE =
                new UtilizatorService();
    }

    public static UtilizatorService getInstance() {
        return Holder.INSTANCE;
    }

    public void add(Utilizator u) {

        AuditService.getInstance()
                .logActiune("utilizator_add");

        if (u == null) {
            throw new IllegalArgumentException(
                    "Utilizator null!"
            );
        }

        if (u.getUsername() == null ||
                u.getUsername().isEmpty()) {

            throw new IllegalArgumentException(
                    "Username invalid!"
            );
        }

        if (u.getEmail() == null ||
                !u.getEmail().contains("@")) {

            throw new IllegalArgumentException(
                    "Email invalid!"
            );
        }

        u.setId(nextId);

        utilizatori.put(nextId, u);

        nextId++;

        if (u instanceof Client) {

            clientRepository.save((Client) u);

        } else if (u instanceof Administrator) {

            administratorRepository.save(
                    (Administrator) u
            );
        }
    }

    public void delete(int id) {

        AuditService.getInstance()
                .logActiune("utilizator_delete");

        Utilizator u = utilizatori.get(id);

        if (u instanceof Client) {

            clientRepository.delete(id);

        } else if (u instanceof Administrator) {

            administratorRepository.delete(id);
        }

        utilizatori.remove(id);
    }

    public Utilizator getById(int id) {

        AuditService.getInstance()
                .logActiune("utilizator_getById");

        Utilizator u = utilizatori.get(id);

        if (u == null) {

            throw new IllegalArgumentException(
                    "Utilizator inexistent!"
            );
        }

        return u;
    }

    public List<Utilizator> getByPrenume(String prenume) {

        AuditService.getInstance()
                .logActiune("utilizator_getByPrenume");

        List<Utilizator> rez = new ArrayList<>();

        for (Utilizator u : utilizatori.values()) {

            if (u.getPrenume().equals(prenume)) {
                rez.add(u);
            }
        }

        return rez;
    }

    public List<Utilizator> getAll() {

        AuditService.getInstance()
                .logActiune("utilizator_getAll");

        return new ArrayList<>(utilizatori.values());
    }

    public List<Administrator> getAdminDupaDep(String nume) {

        AuditService.getInstance()
                .logActiune("utilizator_getAdminDupaDep");

        List<Administrator> lista =
                new ArrayList<>();

        for (Utilizator u : utilizatori.values()) {

            if (u instanceof Administrator) {

                Administrator a = (Administrator) u;

                if (a.getDepartament().equals(nume)) {
                    lista.add(a);
                }
            }
        }

        lista.sort(
                Comparator.comparing(
                        Administrator::getNume
                ).thenComparing(
                        Administrator::getPrenume
                )
        );

        return lista;
    }

    public List<Client> getClientiSortati() {

        AuditService.getInstance()
                .logActiune("utilizator_getClientiSortati");

        List<Client> lista = new ArrayList<>();

        for (Utilizator u : utilizatori.values()) {

            if (u instanceof Client) {
                lista.add((Client) u);
            }
        }

        lista.sort(
                Comparator.comparing(
                        Client::getUsername
                )
        );

        return lista;
    }
}