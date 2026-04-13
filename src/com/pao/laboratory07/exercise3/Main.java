package com.pao.laboratory07.exercise3;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());
        List<Comanda> comenzi = new ArrayList<>();


        for (int i = 0; i < n; i++) {
            String[] parts = scanner.nextLine().split(" ");
            String tip = parts[0];

            switch (tip) {
                case "STANDARD" -> {
                    comenzi.add(new ComandaStandard(
                            parts[1],
                            Double.parseDouble(parts[2]),
                            parts[3]
                    ));
                }
                case "DISCOUNTED" -> {
                    comenzi.add(new ComandaRedusa(
                            parts[1],
                            Double.parseDouble(parts[2]),
                            Integer.parseInt(parts[3]),
                            parts[4]
                    ));
                }
                case "GIFT" -> {
                    comenzi.add(new ComandaGratuita(
                            parts[1],
                            parts[2]
                    ));
                }
            }
        }

        comenzi.forEach(c -> System.out.println(c.descriere() + " - client: " + c.client));

        while (true) {
            String line = scanner.nextLine();

            if (line.equals("QUIT")) break;

            if (line.equals("STATS")) {
                System.out.println("\n--- STATS ---");

                Map<String, Double> medii = comenzi.stream()
                        .collect(Collectors.groupingBy(
                                c -> {
                                    if (c instanceof ComandaStandard) return "STANDARD";
                                    if (c instanceof ComandaRedusa) return "DISCOUNTED";
                                    return "GIFT";
                                },
                                Collectors.averagingDouble(Comanda::pretFinal)
                        ));

                medii.forEach((k, v) ->
                        System.out.printf("%s: medie = %.2f lei%n", k, v)
                );
            }

            else if (line.startsWith("FILTER")) {
                double threshold = Double.parseDouble(line.split(" ")[1]);

                System.out.printf("\n--- FILTER (>= %.2f) ---%n", threshold);

                comenzi.stream()
                        .filter(c -> c.pretFinal() >= threshold)
                        .forEach(c -> System.out.printf("%s, pret: %.2f lei - client: %s%n",
                                c instanceof ComandaStandard ? "STANDARD: " + c.nume :
                                        c instanceof ComandaRedusa ? "DISCOUNTED: " + c.nume :
                                                "GIFT: " + c.nume,
                                c.pretFinal(),
                                c.client));
            }

            else if (line.equals("SORT")) {
                System.out.println("\n--- SORT (by client, then by pret) ---");

                comenzi.stream()
                        .sorted(Comparator
                                .comparing(Comanda::getClient)
                                .thenComparing(Comanda::pretFinal))
                        .forEach(c -> System.out.printf("%s, pret: %.2f lei - client: %s%n",
                                c instanceof ComandaStandard ? "STANDARD: " + c.nume :
                                        c instanceof ComandaRedusa ? "DISCOUNTED: " + c.nume :
                                                "GIFT: " + c.nume,
                                c.pretFinal(),
                                c.client));
            }

            else if (line.equals("SPECIAL")) {
                System.out.println("\n--- SPECIAL (discount > 15%) ---");

                comenzi.stream()
                        .filter(c -> c instanceof ComandaRedusa cr && cr.getDiscountProcent() > 15)
                        .forEach(c -> {
                            ComandaRedusa cr = (ComandaRedusa) c;
                            System.out.printf("DISCOUNTED: %s, pret: %.2f lei (-%d%%) - client: %s%n",
                                    cr.nume, cr.pretFinal(), cr.getDiscountProcent(), cr.client);
                        });
            }
        }
    }
}