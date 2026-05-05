package com.pao.laboratory08.exercise2;
import com.pao.laboratory08.exercise1.Student;
import com.pao.laboratory08.exercise1.Adresa;
import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";
    private static final String OUTPUT_FILE = "src/com/pao/laboratory08/exercise2/rezultate3.txt";

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        int vrst = scanner.nextInt();
        List<Student> studenti = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE));
        String line;
        int cnt = 0;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue; 

            String[] parts = line.split(",");

            String nume = parts[0].trim();
            int varsta = Integer.parseInt(parts[1].trim());
            String oras = parts[2].trim();
            String strada = parts[3].trim();

            if(varsta >= vrst){
                studenti.add(new Student(nume, varsta, new Adresa(oras, strada)));
                cnt+=1;
            }
        }

        br.close();

        System.out.println("Filtru: varsta >= " + vrst);
        System.out.println("Rezultate: " + studenti.size() + " studenti");
        System.out.println();

        for(Student s:studenti){
            String std = s.toString();
            bw.write(std);
            bw.newLine();

            System.out.println(std);
        }
        bw.close();
        System.out.println(); 
        System.out.println("Scris in: " + OUTPUT_FILE);
    }
}

