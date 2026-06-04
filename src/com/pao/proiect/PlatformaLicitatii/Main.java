package com.pao.proiect.PlatformaLicitatii;

import com.pao.proiect.PlatformaLicitatii.model.*;
import com.pao.proiect.PlatformaLicitatii.service.*;
import com.pao.proiect.PlatformaLicitatii.repository.ProdusRepository;
import com.pao.proiect.PlatformaLicitatii.util.DatabaseInitializer;
import com.pao.proiect.PlatformaLicitatii.exception.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Main {

    private static Map<Integer, List<Oferta>> istoricPersonal = new HashMap<>();

    private static int getClientDbId(String username) {
        try {
            Connection conn = com.pao.proiect.PlatformaLicitatii.util.DatabaseConnection
                    .getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT id FROM client WHERE username = ?"
            );
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {

        DatabaseInitializer.initialize();

        UtilizatorService utilizatorService = UtilizatorService.getInstance();
        LicitatieService licitatieService = LicitatieService.getInstance();
        OfertaService ofertaService = OfertaService.getInstance();

        ProdusRepository produsRepository = new ProdusRepository();

        try (Scanner scanner = new Scanner(System.in)) {
            Utilizator utilizatorLogat = null;

            while (true) {

                System.out.println("\n===== SISTEM LICITATII =====");

                if (utilizatorLogat == null) {

                    System.out.println("0. Login");
                    System.out.println("1. Adauga utilizator");
                    System.out.println("2. Afiseaza utilizatori");
                    System.out.println("3. Exit");

                    int opt = scanner.nextInt();

                    switch (opt) {

                        case 0:
                            System.out.println("username:");
                            String user = scanner.next();

                            utilizatorLogat = null;

                            for (Utilizator u : utilizatorService.getAll()) {
                                if (u.getUsername().equals(user)) {
                                    utilizatorLogat = u;
                                    System.out.println("Logat ca: " + u.getUsername() + " (" + u.getRol() + ")");
                                    break;
                                }
                            }

                            if (utilizatorLogat == null) {
                                System.out.println("User inexistent!");
                            }
                            break;

                        case 1:
                            System.out.println("nume prenume email username rol");

                            String n = scanner.next();
                            String p = scanner.next();
                            String e = scanner.next();
                            String u = scanner.next();
                            String r = scanner.next();

                            if (r.equals("CLIENT")) {
                                System.out.println("buget");
                                double buget = scanner.nextDouble();
                                utilizatorService.add(new Client(0, n, p, e, u, buget));
                            } else {
                                System.out.println("departament");
                                String departament = scanner.next();
                                utilizatorService.add(new Administrator(0, n, p, e, u, departament));
                            }
                            break;

                        case 2:
                            for (Utilizator us : utilizatorService.getAll()) {
                                System.out.println(us);
                            }
                            break;

                        case 3:
                            return;
                    }

                } else {

                    if (utilizatorLogat.getRol() == Rol.CLIENT) {

                        System.out.println("\n--- MENIU CLIENT ---");
                        System.out.println("1. Vezi licitatii");
                        System.out.println("2. Adauga oferta");
                        System.out.println("3. Istoric personal");
                        System.out.println("4. Afisare sold");
                        System.out.println("5. Cautare licitatie dupa numele unui produs");
                        System.out.println("6. Afisare licitatii crescator dupa pret");
                        System.out.println("7. Raport produse (JOIN)");
                        System.out.println("8. Logout");

                        int opt = scanner.nextInt();

                        switch (opt) {

                            case 2:
                                try {
                                    System.out.println("\n=== LICITATII DISPONIBILE ===");
                                    for (Licitatie l : licitatieService.getAll()) {
                                        if (l.getStatus() == StatusLicitatie.ACTIVA) {
                                            System.out.println(l.getId() + " | " + l.getProdus().getNume() + " | " + l.getProdus().getPretStart());
                                        }
                                    }

                                    System.out.println("licitatieId suma");

                                    int lid = scanner.nextInt();
                                    double suma = scanner.nextDouble();

                                    Client c = (Client) utilizatorLogat;

                                    Oferta oferta = new Oferta(0, c, suma, StatusOferta.PENDING);

                                    licitatieService.getById(lid).adaugaOferta(oferta);

                                    int clientDbId = getClientDbId(c.getUsername());
                                    ofertaService.insertOferta(clientDbId, lid, suma);

                                    List<Oferta> lista = istoricPersonal.get(c.getId());
                                    if (lista == null) {
                                        lista = new ArrayList<>();
                                        istoricPersonal.put(c.getId(), lista);
                                    }
                                    lista.add(oferta);

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 1:
                                System.out.println("\n=== LICITATII ===");
                                for (Licitatie l : licitatieService.getAll()) {
                                    System.out.println(l.getId() + " | " + l.getProdus().getNume() + " | " + l.getStatus());
                                }
                                break;

                            case 3:
                                List<Oferta> istoric = istoricPersonal.get(utilizatorLogat.getId());

                                if (istoric == null || istoric.isEmpty()) {
                                    System.out.println("Nu ai oferte.");
                                } else {
                                    for (Oferta o : istoric) {
                                        System.out.println(o);
                                    }
                                }
                                break;

                            case 4:
                                ((Client) utilizatorLogat).afiseazaSold();
                                break;

                            case 5:
                                System.out.println("Nume produs");
                                String numeProdus = scanner.next();
                                List<Licitatie> lista = licitatieService.getByNumeProdus(numeProdus);
                                if (lista.isEmpty())
                                    System.out.println("Nu exista licitatii pentru acest produs.");
                                else
                                    for (Licitatie l : lista) {
                                        System.out.println(l);
                                    }
                                break;

                            case 6:
                                List<Licitatie> lic_sor = licitatieService.getLicitatiiSortateDupaPret();
                                if (lic_sor.isEmpty())
                                    System.out.println("Nu exista licitatii.");
                                else
                                    for (Licitatie l : lic_sor) {
                                        System.out.println(l);
                                    }
                                break;

                            case 7:
                                produsRepository.raportProduseCuLicitatiiSiOferte();
                                break;

                            case 8:
                                utilizatorLogat = null;
                                break;
                        }
                    }

                    else if (utilizatorLogat.getRol() == Rol.ADMIN) {

                        System.out.println("\n--- MENIU ADMINISTRATOR ---");
                        System.out.println("1. Creeaza licitatie");
                        System.out.println("2. Aproba oferta");
                        System.out.println("3. Inchide licitatie");
                        System.out.println("4. Tranzactii sortate");
                        System.out.println("5. Afisare admini in functie de departament");
                        System.out.println("6. Afisare clienti sortati dupa username");
                        System.out.println("7. Afisare oferte sortate");
                        System.out.println("8. Respinge oferta");
                        System.out.println("9. Afisare licitatii sortate dupa pret");
                        System.out.println("10. JOIN: Top clienti dupa oferte");
                        System.out.println("11. JOIN: Raport oferte complete");
                        System.out.println("12. JOIN: Raport licitatii cu nr oferte");
                        System.out.println("13. Logout");

                        int opt = scanner.nextInt();

                        switch (opt) {

                            case 1:
                                System.out.println("nume pret categorie");

                                String numeP = scanner.next();
                                double pret = scanner.nextDouble();
                                String catN = scanner.next();

                                CategorieProdus cat = new CategorieProdus(catN);

                                Produs p = licitatieService.creeazaProdus(numeP, pret, cat);

                                Licitatie licitatie = new Licitatie(0, p, StatusLicitatie.ACTIVA);

                                licitatieService.adauga(licitatie);
                                break;

                            case 2:
                                try {
                                    System.out.println("\n=== LICITATII ===");
                                    for (Licitatie l : licitatieService.getAll()) {
                                        System.out.println(l.getId() + " | " + l.getProdus().getNume());
                                    }

                                    System.out.println("licitatieId:");
                                    int lid = scanner.nextInt();

                                    Licitatie lic = licitatieService.getById(lid);

                                    System.out.println("\n=== OFERTE ===");
                                    for (Oferta o : lic.getOferteInAsteptare()) {
                                        System.out.println(o.getId() + " | " + o.getClient().getUsername() + " | " + o.getSuma() + " | " + o.getStatus());
                                    }

                                    System.out.println("ofertaId:");
                                    int oid = scanner.nextInt();

                                    licitatieService.aprobaOferta(lid, oid);

                                } catch (OfertaInexistentaException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 3:
                                for (Licitatie l : licitatieService.getAll()) {
                                    System.out.println(l.getId() + " | " + l.getProdus().getNume());
                                }
                                System.out.println("licitatieId");
                                int lid2 = scanner.nextInt();
                                licitatieService.inchideLicitatie(lid2);
                                break;

                            case 4:
                                licitatieService.afiseazaTranzactiiSortate();
                                break;

                            case 5:
                                System.out.println("Nume departament");
                                String numeD = scanner.next();
                                List<Administrator> listaAdm = utilizatorService.getAdminDupaDep(numeD);
                                for (Administrator a : listaAdm) {
                                    System.out.println(a);
                                }
                                break;

                            case 6:
                                List<Client> lista_cl = utilizatorService.getClientiSortati();
                                for (Client c : lista_cl) {
                                    System.out.println(c);
                                }
                                break;

                            case 7:
                                for (Licitatie l : licitatieService.getAll()) {
                                    System.out.println(l.getId() + " | " + l.getProdus().getNume());
                                }
                                System.out.println("licitatieId");
                                int lid3 = scanner.nextInt();
                                licitatieService.afiseazaOferteSortate(lid3);
                                break;

                            case 8:
                                try {
                                    System.out.println("\n=== LICITATII ===");
                                    for (Licitatie l : licitatieService.getAll()) {
                                        System.out.println(l.getId() + " | " + l.getProdus().getNume());
                                    }

                                    System.out.println("licitatieId:");
                                    int lid = scanner.nextInt();

                                    Licitatie lic = licitatieService.getById(lid);

                                    System.out.println("\n=== OFERTE ===");
                                    for (Oferta o : lic.getOferteInAsteptare()) {
                                        System.out.println(o.getId() + " | " + o.getClient().getUsername() + " | " + o.getSuma() + " | " + o.getStatus());
                                    }

                                    System.out.println("ofertaId:");
                                    int oid = scanner.nextInt();

                                    licitatieService.respingeOferta(lid, oid);

                                } catch (OfertaInexistentaException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            
                            case 9:
                                List<Licitatie> ll = licitatieService.getLicitatiiSortateDupaPret();
                                for(Licitatie i : ll){
                                    System.out.println(i);
                                }
                                break;

                            case 10:
                                ofertaService.raportTopClientiDupaOferte();
                                break;

                            case 11:
                                ofertaService.raportOferteComplete();
                                break;

                            case 12:
                                licitatieService.raportLicitatiiCuNumarOferte();
                                break;

                            case 13:
                                utilizatorLogat = null;
                                break;
                        }
                    }
                }
            }
        }
    }
}