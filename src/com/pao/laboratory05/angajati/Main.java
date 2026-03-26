package com.pao.laboratory05.angajati;
import java.util.Scanner;
/**
 * Exercise 3 — Angajați
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 3 — Angajați"
 *
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AngajatService ang = AngajatService.getInstance();
        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");

            int option = scanner.nextInt();

            switch(option){
                case 1:
                    System.out.print("Nume: ");
                    String nume = scanner.next();
                    System.out.print("Nume departament: ");
                    String numeDepartament = scanner.next();
                    System.out.print("Locatie departament: ");
                    String locatieDepartament = scanner.next();
                    System.out.print("Salariu: ");
                    double salariu = scanner.nextDouble();
                    Departament departament = new Departament(numeDepartament,locatieDepartament);
                    Angajat a = new Angajat(nume,departament,salariu);
                    ang.addAngajat(a);
                    break;
                case 2:
                    ang.listBySalary();
                    break;
                case 3:
                    System.out.print("Departament: ");
                    String n_dep = scanner.next();
                    System.out.print("Angajati din: " + n_dep);
                    ang.findByDepartament(n_dep);
                    break;
                case 0:
                    System.out.println("La revedere!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }
    }
}
