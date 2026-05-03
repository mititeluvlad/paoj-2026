package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {

        List<Student> studenti = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue; 

            String[] parts = line.split(",");

            String nume = parts[0].trim();
            int varsta = Integer.parseInt(parts[1].trim());
            String oras = parts[2].trim();
            String strada = parts[3].trim();

            studenti.add(new Student(nume, varsta, new Adresa(oras, strada)));
        }

        br.close();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[] cmd = input.split(" ", 2);

        if (cmd[0].equals("PRINT")) {
            for (Student s : studenti) {
                System.out.println(s);
            }
        }

        else if (cmd[0].equals("SHALLOW")) {

            if (cmd.length < 2) return;
            String nume = cmd[1];

            for (Student s : studenti) {
                if (s.getNume().equals(nume)) {
                    Student clone = (Student) s.clone();

                    clone.getAdresa().setOras("MODIFICAT");

                    System.out.println("Original: " + s);
                    System.out.println("Clona: " + clone);
                }
            }
        }

        else if (cmd[0].equals("DEEP")) {
            if (cmd.length < 2) return;

            String nume = cmd[1];

            for (Student s : studenti) {
                if (s.getNume().equals(nume)) {
                    Student clone = (Student) s.deepClone();

                    clone.getAdresa().setOras("MODIFICAT");

                    System.out.println("Original: " + s);
                    System.out.println("Clona: " + clone);
                }
            }
        }
    }
}