package com.pao.test;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;

/**
 * Utilitar pentru testarea automată a exercițiilor I/O.
 */
public class IOTest {

    @FunctionalInterface
    public interface MainMethod {
        void run(String[] args) throws Exception;
    }

    // ── Metode pentru structură cu subdirectoare (partA, partB...) ──────────

    public static void runParts(String testsDir, MainMethod main) {
        runParts(testsDir, main, false);
    }

    public static void runParts(String testsDir, MainMethod main, boolean printFullOutput) {
        File dir = new File(testsDir);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("EROARE: directorul de teste nu există: " + dir.getAbsolutePath());
            return;
        }

        File[] all = dir.listFiles();
        File[] partDirs = (all == null) ? new File[0]
                : Arrays.stream(all).filter(File::isDirectory).toArray(File[]::new);
        Arrays.sort(partDirs, Comparator.comparing(File::getName));

        if (partDirs.length == 0) {
            System.out.println("EROARE: nu există subdirectoare de tip partX/ în " + testsDir);
            return;
        }

        List<String> partNames = new ArrayList<>();
        List<Integer> partPassed = new ArrayList<>();
        List<Integer> partTotal = new ArrayList<>();

        for (File partDir : partDirs) {
            String partName = partDir.getName();
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.printf("║  Partea: %-52s║%n", partName);
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

            int[] results = runPartDir(partDir, main, printFullOutput);
            partNames.add(partName);
            partPassed.add(results[0]);
            partTotal.add(results[1]);
        }

        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  SUMAR FINAL");
        System.out.println("──────────────────────────────────────────────────────────────");
        int totalP = 0, totalT = 0;
        for (int i = 0; i < partNames.size(); i++) {
            int p = partPassed.get(i), t = partTotal.get(i);
            String status = (p == t && t > 0) ? "[OK]" : (p == 0 ? "[FAIL]" : "[PARTIAL]");
            System.out.printf("  %s  %-10s  %d/%d teste%n", status, partNames.get(i), p, t);
            totalP += p; totalT += t;
        }
        System.out.println("──────────────────────────────────────────────────────────────");
        System.out.printf("  Total: %d/%d teste trecute%n", totalP, totalT);
        System.out.println("══════════════════════════════════════════════════════════════");
    }

    // ── Metode pentru structură PLATĂ (fără subdirectoare) ──────────────────

    /**
     * Rulează testele direct dintr-un director (fără subdirectoare partX/).
     */
    public static void runFlat(String testsDir, MainMethod main) {
        runFlat(testsDir, main, false);
    }

    /**
     * Rulează testele dintr-un director plat, cu opțiune de afișare completă.
     */
    public static void runFlat(String testsDir, MainMethod main, boolean printFullOutput) {
        File dir = new File(testsDir);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("EROARE: directorul de teste nu există: " + dir.getAbsolutePath());
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.printf("║  Teste: %-53s║%n", testsDir);
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        int[] results = runPartDir(dir, main, printFullOutput);
        
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.printf("  Total: %d/%d teste trecute%n", results[0], results[1]);
        System.out.println("══════════════════════════════════════════════════════════════");
    }

    // ── Internal helpers ─────────────────────────────────────────────────────

    private static int[] runPartDir(File dir, MainMethod main, boolean printFullOutput) {
        File[] all = dir.listFiles();
        File[] inFiles = (all == null) ? new File[0]
                : Arrays.stream(all).filter(f -> f.getName().endsWith(".in")).toArray(File[]::new);
        
        if (inFiles.length == 0) {
            System.out.println("  (niciun fișier .in găsit în " + dir.getPath() + ")");
            return new int[]{0, 0};
        }
        
        Arrays.sort(inFiles, Comparator.comparing(File::getName));
        int passed = 0, failed = 0;

        for (File inFile : inFiles) {
            String base = inFile.getName().replace(".in", "");
            File outFile = new File(dir, base + ".out");
            
            System.out.println("\n  *** Test: " + base + " ***");

            if (!outFile.exists()) {
                System.out.println("  [SKIP] " + base + " — lipsește " + base + ".out");
                continue;
            }

            try {
                String input = Files.readString(inFile.toPath()).replace("\r\n", "\n");
                String expected = Files.readString(outFile.toPath()).stripTrailing().replace("\r\n", "\n");
                String actual = capture(input, main);
                
                if (actual == null) {
                    System.out.println("  [FAIL] " + base + " — excepție la rulare");
                    failed++;
                    continue;
                }

                actual = actual.stripTrailing().replace("\r\n", "\n");

                if (actual.equals(expected)) {
                    System.out.println("  [PASS] " + base);
                    passed++;
                } else {
                    System.out.println("  [FAIL] " + base);
                    printUnifiedDiff(expected, actual, dir, base);
                    failed++;
                }
            } catch (IOException e) {
                System.out.println("  [ERROR] " + e.getMessage());
                failed++;
            }
            System.out.println("  -------------------------------------------------------------");
        }
        return new int[]{passed, passed + failed};
    }

    private static String capture(String input, MainMethod main) {
        InputStream savedIn = System.in;
        PrintStream savedOut = System.out;
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));
            main.run(new String[0]);
            System.out.flush();
            return baos.toString();
        } catch (Exception e) {
            return null;
        } finally {
            System.setIn(savedIn);
            System.setOut(savedOut);
        }
    }

    private static void printUnifiedDiff(String expected, String actual, File dir, String base) {
        List<String> expectedLines = Arrays.asList(expected.split("\n", -1));
        List<String> actualLines = Arrays.asList(actual.split("\n", -1));
        Patch<String> patch = DiffUtils.diff(expectedLines, actualLines);
        List<String> unifiedDiff = UnifiedDiffUtils.generateUnifiedDiff("expected", "actual", expectedLines, patch, 3);
        
        System.out.println("  ╔═══ Diff ═══════════════");
        for (String line : unifiedDiff) {
            System.out.println("  ║    " + line);
        }
        System.out.println("  ╚════════════════════════");

        // Salvare în folderul results (la nivelul părintelui directorului de teste)
        try {
            File resultsDir = new File(dir.getParentFile(), "results");
            if (!resultsDir.exists()) resultsDir.mkdirs();
            File diffFile = new File(resultsDir, dir.getName() + "-" + base + ".diff");
            Files.write(diffFile.toPath(), unifiedDiff);
        } catch (Exception ignored) {}
    }
}