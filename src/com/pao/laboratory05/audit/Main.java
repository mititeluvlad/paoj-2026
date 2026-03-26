package com.pao.laboratory05.audit;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AngajatService ang = AngajatService.getInstance();
        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("4. Afișează audit log");
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
                case 4:
                    System.out.println("Audit log: ");
                    ang.printAuditLog();
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
