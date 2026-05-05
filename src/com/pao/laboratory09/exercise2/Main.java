package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {

    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {

        File file = new File(OUTPUT_FILE);
        file.getParentFile().mkdirs();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine().trim());

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {

            for (int i = 0; i < N; i++) {

                String[] p = br.readLine().trim().split("\\s+");

                int id = Integer.parseInt(p[0]);
                double suma = Double.parseDouble(p[1]);
                String data = p[2];
                TipTranzactie tip = TipTranzactie.valueOf(p[3]);

                ByteBuffer buffer = ByteBuffer.allocate(RECORD_SIZE);
                buffer.order(ByteOrder.LITTLE_ENDIAN);

                buffer.putInt(id);
                buffer.putDouble(suma);

                StringBuilder sb = new StringBuilder(data);
                while (sb.length() < 10) sb.append(' ');
                buffer.put(sb.toString().getBytes());

                byte tipByte = (byte) (tip == TipTranzactie.CREDIT ? 0 : 1);
                buffer.put(tipByte);

                buffer.put((byte) 0);

                for (int j = 0; j < 8; j++) buffer.put((byte) 0);

                raf.write(buffer.array());
            }

            String line;
            while ((line = br.readLine()) != null) {

                line = line.trim();
                if (line.isEmpty()) continue;

                String[] c = line.split(" ");

                switch (c[0]) {

                    case "READ": {
                        int idx = Integer.parseInt(c[1]);
                        raf.seek((long) idx * RECORD_SIZE);

                        byte[] rec = new byte[RECORD_SIZE];
                        raf.readFully(rec);

                        print(rec, idx);
                        break;
                    }

                    case "UPDATE": {
                        int idx = Integer.parseInt(c[1]);
                        String statusStr = c[2];

                        byte status = switch (statusStr) {
                            case "PENDING" -> 0;
                            case "PROCESSED" -> 1;
                            case "REJECTED" -> 2;
                            default -> 0;
                        };

                        raf.seek((long) idx * RECORD_SIZE + 23);
                        raf.writeByte(status);

                        System.out.println("Updated [" + idx + "]: " + statusStr);
                        break;
                    }

                    case "PRINT_ALL": {
                        for (int i = 0; i < N; i++) {
                            raf.seek((long) i * RECORD_SIZE);

                            byte[] rec = new byte[RECORD_SIZE];
                            raf.readFully(rec);

                            print(rec, i);
                        }
                        break;
                    }
                }
            }
        }
    }

    private static void print(byte[] rec, int idx) {

        ByteBuffer b = ByteBuffer.wrap(rec);
        b.order(ByteOrder.LITTLE_ENDIAN);

        int id = b.getInt();
        double suma = b.getDouble();

        byte[] dataBytes = new byte[10];
        b.get(dataBytes);
        String data = new String(dataBytes).trim();

        byte tip = b.get();
        byte status = b.get();

        String tipStr = (tip == 0) ? "CREDIT" : "DEBIT";

        String statusStr = switch (status) {
            case 0 -> "PENDING";
            case 1 -> "PROCESSED";
            case 2 -> "REJECTED";
            default -> "UNKNOWN";
        };

        System.out.printf("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s%n",
                idx, id, data, tipStr, suma, statusStr);
    }
}