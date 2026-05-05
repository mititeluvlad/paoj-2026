package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {

    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {

        File file = new File(OUTPUT_FILE);
        file.getParentFile().mkdirs();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        List<Tranzactie> lista = new ArrayList<>();

        String line = br.readLine();

        int N = Integer.parseInt(line.trim());

        for (int i = 0; i < N; i++) {
            String[] parts = br.readLine().trim().split(" ");

            int id = Integer.parseInt(parts[0]);
            double suma = Double.parseDouble(parts[1]);
            String data = parts[2];
            String contSursa = parts[3];
            String contDest = parts[4];
            TipTranzactie tip = TipTranzactie.valueOf(parts[5]);

            Tranzactie t = new Tranzactie(id, suma, data, contSursa, contDest, tip);
            t.setNote("procesat");
            lista.add(t);
        }

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(lista);
        }

        List<Tranzactie> listaCitita;

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(file))) {
            listaCitita = (List<Tranzactie>) ois.readObject();
        }

        String cmdLine;
        while ((cmdLine = br.readLine()) != null) {

            cmdLine = cmdLine.trim();

            String[] cmd = cmdLine.split(" ");

            switch (cmd[0]) {

                case "LIST":
                    for (Tranzactie t : listaCitita) {
                        System.out.println(t);
                    }
                    break;

                case "FILTER":
                    boolean gasit = false;
                    for (Tranzactie t : listaCitita) {
                        if (t.getData().startsWith(cmd[1])) {
                            System.out.println(t);
                            gasit = true;
                        }
                    }
                    if (!gasit) {
                        System.out.println("Niciun rezultat.");
                    }
                    break;

                case "NOTE":
                    int id = Integer.parseInt(cmd[1]);
                    boolean found = false;

                    for (Tranzactie t : listaCitita) {
                        if (t.getId() == id) {
                            System.out.println("NOTE[" + id + "]: " + t.getNote());
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("NOTE[" + id + "]: not found");
                    }
                    break;
            }
        }
    }
}