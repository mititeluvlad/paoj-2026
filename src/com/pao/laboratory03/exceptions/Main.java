package com.pao.laboratory03.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercițiul 3 — Excepții (checked, unchecked, custom)
 *
 * Creează în acest pachet (lângă Main.java) două clase de excepții custom,
 * apoi demonstrează-le aici.
 *
 * PASUL 1 — Creează InvalidAgeException.java (fișier separat):
 *   - Extinde RuntimeException (unchecked)
 *   - Constructor cu String message → apelează super(message)
 *
 * PASUL 2 — Creează DuplicateEntryException.java (fișier separat):
 *   - Extinde RuntimeException (unchecked)
 *   - Constructor cu String message → apelează super(message)
 *
 * PASUL 3 — În acest Main.java, implementează și demonstrează:
 *
 *   a) UNCHECKED EXCEPTIONS — NullPointerException, ArrayIndexOutOfBoundsException:
 *      - Creează o metodă riskyMethod() care aruncă NullPointerException
 *      - Prinde-o cu try-catch, afișează mesajul erorii
 *      - Adaugă un bloc finally care se execută mereu
 *
 *   b) CUSTOM EXCEPTIONS — InvalidAgeException, DuplicateEntryException:
 *      - Creează o metodă validateAge(int age) care aruncă InvalidAgeException
 *        dacă age < 0 sau age > 150
 *      - Creează o metodă addToList(List<String> list, String name) care aruncă
 *        DuplicateEntryException dacă name există deja în listă
 *      - Demonstrează ambele cu try-catch
 *
 *   c) MULTI-CATCH:
 *      - Prinde InvalidAgeException | DuplicateEntryException într-un singur catch
 *
 *   d) CATCH ORDERING:
 *      - Demonstrează că prinderea specifică (InvalidAgeException) trebuie
 *        să fie ÎNAINTE de cea generală (RuntimeException)
 *
 *   e) THROW vs THROWS:
 *      - Creează o metodă cu semnătura: void process(int age) throws InvalidAgeException
 *      - Apeleaz-o din main cu try-catch
 *
 * Output așteptat:
 *
 * === a) Unchecked — NullPointerException ===
 * Prins: Cannot invoke "String.length()" because "s" is null
 * Finally se execută mereu!
 *
 * === b) Custom exceptions ===
 * InvalidAgeException: Vârsta -5 nu este validă (0-150)
 * DuplicateEntryException: 'Ana' există deja în listă
 *
 * === c) Multi-catch ===
 * Excepție prinsă: Vârsta 200 nu este validă (0-150)
 *
 * === d) Catch ordering (specific → general) ===
 * InvalidAgeException prinsă specific: Vârsta -1 nu este validă (0-150)
 *
 * === e) Throw vs throws ===
 * Metoda process() a aruncat: Vârsta 999 nu este validă (0-150)
 */
public class Main {
    public static void main(String[] args) {
        // TODO: implementează pașii de mai sus
        // Hint: creează mai întâi InvalidAgeException.java și DuplicateEntryException.java

        riskyMethod1();

        riskyMethod2();

        try{
            int varsta = validateAge(-5);
        }
        catch(InvalidAgeException e){
            System.out.println("Exceptie: " + e.getMessage());
        }

        List<String> names = new ArrayList<>();
        names.add("Ana");
        names.add("Ion");

         try{
            String name = addToList(names, "Ion");
        }
        catch(DuplicateEntryException e){
            System.out.println("Exceptie: " + e.getMessage());
        }

        try {
            int age = validateAge(200);
            String name = addToList(names, "Ion");
        } catch (InvalidAgeException | DuplicateEntryException e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        try {
            int age = validateAge(-100);
        } catch (InvalidAgeException e) {
            System.out.println("Specific: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("General: " + e.getMessage());
        }

        try{
            process(-223);
        }
        catch(InvalidAgeException e){
            System.out.println("Exception: " + e.getMessage());
            throw e;
        }

    }
    public static void riskyMethod1(){
         try {
            String text = null;
            text.toUpperCase();  // NullPointerException
        } catch (NullPointerException e) {
            System.out.println("Prins: " + e.getMessage());
        } finally {
            System.out.println("Finally — se execută MEREU, chiar și cu return!");
        }
    }
    public static void riskyMethod2(){
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[10]);  // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Prins: " + e.getMessage());
        }
    }
    public static int validateAge(int age){
        if(age < 0 || age > 150)
            throw new InvalidAgeException("Varsta invalida");
        return age;
    }
    public static String addToList(List<String> list, String name){
        for(int i = 0; i < list.size(); ++i){
            if(list.get(i).equals(name)){
                throw new DuplicateEntryException("Duplicate");
            }
        }
        return name;
    }

    public static void process(int age) throws InvalidAgeException{
        int var = validateAge(age);
    }
}

