package com.pao.proiect.PlatformaLicitatii.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuditService {

    private static final String FILE_NAME = "audit.csv";

    private AuditService() {}

    private static class Holder {
        private static final AuditService INSTANCE = new AuditService();
    }

    public static AuditService getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void logActiune(String actiune) {

        String timestamp = LocalDateTime.now().toString();
        String linie = actiune + "," + timestamp + "\n";

        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(linie);
            fw.flush();
        } catch (IOException e) {
            System.out.println("Eroare audit: " + e.getMessage());
        }
    }
}