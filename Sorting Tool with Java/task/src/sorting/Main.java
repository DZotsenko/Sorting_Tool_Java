package sorting;

import java.util.*;
import java.io.*;

public class Main {
    private static String sortingType = "natural";

    public static void getMaxNumber(Scanner scanner, PrintStream output) {
        ArrayList<Long> numbers = new ArrayList<>();
        while (scanner.hasNext()) {
            if (scanner.hasNextLong()) {
                long number = scanner.nextLong();
                numbers.add(number);
            } else {
                String input = scanner.next();
                output.printf("\"%s\" is not a long. It will be skipped.%n", input);
            }
        }
        output.println("Total numbers: " + numbers.size() + ".");
        if (sortingType.equals("natural")) {
            Collections.sort(numbers);
            output.print("Sorted data: ");
            numbers.forEach(number -> output.print(number + " "));
        } else if (sortingType.equals("byCount")) {
            Map<Long, Integer> frequencyMap = new HashMap<>();
            numbers.forEach(num -> frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1));

            frequencyMap.entrySet().stream()
                    .sorted(Map.Entry.<Long, Integer>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEach(entry -> {
                        double percentage = (double) entry.getValue() / numbers.size() * 100;
                        output.printf("%d: %d time(s), %.0f%%%n", entry.getKey(), entry.getValue(), percentage);
                    });
        }
    }

    public static void getLongestLine(Scanner scanner, PrintStream output) {
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        output.println("Total lines: " + lines.size() + ".");
        if (sortingType.equals("natural")) {
            Collections.sort(lines);
            output.println("Sorted data:");
            lines.forEach(output::println);
        } else if (sortingType.equals("byCount")) {
            Map<String, Integer> frequencyMap = new HashMap<>();
            lines.forEach(line -> frequencyMap.put(line, frequencyMap.getOrDefault(line, 0) + 1));

            frequencyMap.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEach(entry -> {
                        double percentage = (double) entry.getValue() / lines.size() * 100;
                        output.printf("%s: %d time(s), %.0f%%%n", entry.getKey(), entry.getValue(), percentage);
                    });
        }
    }

    public static void getLongestWord(Scanner scanner, PrintStream output) {
        ArrayList<String> words = new ArrayList<>();
        while (scanner.hasNext()) {
            String word = scanner.next();
            words.add(word);
        }
        output.println("Total words: " + words.size() + ".");
        if (sortingType.equals("natural")) {
            Collections.sort(words);
            output.print("Sorted data: ");
            words.forEach(word -> output.print(word + " "));
        } else if (sortingType.equals("byCount")) {
            Map<String, Integer> frequencyMap = new HashMap<>();
            words.forEach(word -> frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1));

            frequencyMap.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEach(entry -> {
                        double percentage = (double) entry.getValue() / words.size() * 100;
                        output.printf("%s: %d time(s), %.0f%%%n", entry.getKey(), entry.getValue(), percentage);
                    });
        }
    }

    public static void main(final String[] args) {
        String dataType = null;
        String inputFile = null;
        String outputFile = null;

        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-sortingType") && i + 1 < args.length) {
                sortingType = args[i + 1];
            }
            if (args[i].equals("-dataType") && i + 1 < args.length) {
                dataType = args[i + 1];
            }
            switch (args[i]) {
                case "-inputFile" -> inputFile = args[i + 1];
                case "-outputFile" -> outputFile = args[i + 1];
            }
        }

        if ((dataType == null) && (inputFile == null)) {
            System.out.println("No data type defined!");
            return;
        }

        Scanner scanner = null;

        try {
            if (inputFile != null) {
                scanner = new Scanner(new File(inputFile));
            } else {
                scanner = new Scanner(System.in);
            }

            PrintStream output;
            if (outputFile != null) {
                output = new PrintStream(new FileOutputStream(outputFile));
            } else {
                output = System.out;
            }

            if (inputFile != null) {
                getMaxNumber(scanner, output);
            }

            if (dataType != null) {
                switch (dataType) {
                    case "long" -> getMaxNumber(scanner, output);
                    case "line" -> getLongestLine(scanner, output);
                    case "word" -> getLongestWord(scanner, output);
                    default -> output.println(dataType + " is not a valid parameter.");
                }
            }

            if (output != System.out) {
                output.close();
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}