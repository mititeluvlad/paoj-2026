package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip) — pot exista duplicate de id
        //    Stochează-le toate într-un ArrayList<Tranzactie> (cu duplicate, ordine inserare)
        //
        // 2. Procesează comenzile din stdin până la EOF:
        //
        //   UNIQUE_IDS      → LinkedHashSet<Integer> cu id-urile în ordinea primei apariții
        //                     afișează: "IDs unice (N): [1, 2, 3, ...]"
        //
        //   MONTHLY_REPORT  → TreeMap<String, ...> grupat pe yyyy-MM (substring 0-7 din data)
        //                     pentru fiecare lună, sumele CREDIT și DEBIT
        //                     format: "yyyy-MM: CREDIT X.XX RON, DEBIT Y.YY RON"
        //
        //   TOP n           → primele n tranzacții după suma descrescătoare (nu modifică lista)
        //                     afișează "Top n:" urmat de n linii
        //
        //   SORT_ASC        → Collections.sort cu suma crescătoare; afișează lista sortată
        //   SORT_DESC       → Collections.sort cu suma descrescătoare; afișează lista sortată
        //   REVERSE         → Collections.reverse; afișează lista
        //   MIN_MAX         → Collections.min/max după suma
        //                     "MIN: [id] data tip: suma RON"
        //                     "MAX: [id] data tip: suma RON"
        //
        //   CME_DEMO        → încearcă for(t : lista) lista.remove(t) în try-catch
        //                     afișează "ConcurrentModificationException prins: modificare in iteratie detectata."
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON

        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());

        List<Tranzactie> lista = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            lista.add(new Tranzactie(id, suma, data, tip));
        }

        while (scanner.hasNext()) {

            String comanda = scanner.next();

            switch (comanda) {

                case "UNIQUE_IDS": {

                    LinkedHashSet<Integer> ids = new LinkedHashSet<>();

                    for (Tranzactie t : lista) {
                        ids.add(t.getId());
                    }

                    System.out.println(
                            "IDs unice (" + ids.size() + "): " + ids
                    );

                    break;
                }

                case "MONTHLY_REPORT": {

                    TreeMap<String, double[]> raport = new TreeMap<>();

                    for (Tranzactie t : lista) {

                        String luna = t.getData().substring(0, 7);

                        raport.putIfAbsent(luna, new double[2]);

                        double[] valori = raport.get(luna);

                        if (t.getTip() == TipTranzactie.CREDIT) {
                            valori[0] += t.getSuma();
                        } else {
                            valori[1] += t.getSuma();
                        }
                    }

                    for (String luna : raport.keySet()) {

                        double[] valori = raport.get(luna);

                        System.out.printf(
                                "%s: CREDIT %.2f RON, DEBIT %.2f RON%n",
                                luna,
                                valori[0],
                                valori[1]
                        );
                    }

                    break;
                }

                case "TOP": {

                    int topN = scanner.nextInt();

                    List<Tranzactie> copie = new ArrayList<>(lista);

                    copie.sort(
                            Comparator.comparingDouble(Tranzactie::getSuma)
                                    .reversed()
                    );

                    System.out.println("Top " + topN + ":");

                    int limita = Math.min(topN, copie.size());

                    List<Tranzactie> topList =
                            copie.subList(0, limita);

                    for (Tranzactie t : topList) {
                        System.out.println(t);
                    }

                    break;
                }

                case "SORT_ASC": {

                    Collections.sort(
                            lista,
                            Comparator.comparingDouble(Tranzactie::getSuma)
                    );

                    for (Tranzactie t : lista) {
                        System.out.println(t);
                    }

                    break;
                }

                case "SORT_DESC": {

                    Collections.sort(
                            lista,
                            Comparator.comparingDouble(Tranzactie::getSuma)
                                    .reversed()
                    );

                    for (Tranzactie t : lista) {
                        System.out.println(t);
                    }

                    break;
                }

                case "REVERSE": {

                    Collections.reverse(lista);

                    for (Tranzactie t : lista) {
                        System.out.println(t);
                    }

                    break;
                }

                case "MIN_MAX": {

                    Tranzactie min = Collections.min(
                            lista,
                            Comparator.comparingDouble(Tranzactie::getSuma)
                    );

                    Tranzactie max = Collections.max(
                            lista,
                            Comparator.comparingDouble(Tranzactie::getSuma)
                    );

                    System.out.println("MIN: " + min);
                    System.out.println("MAX: " + max);

                    break;
                }

                case "CME_DEMO": {

                    try {

                        for (Tranzactie t : lista) {
                            lista.remove(t);
                        }

                    } catch (ConcurrentModificationException e) {

                        System.out.println(
                                "ConcurrentModificationException prins: modificare in iteratie detectata."
                        );
                    }

                    break;
                }
            }
        }

        scanner.close();
    }
}
