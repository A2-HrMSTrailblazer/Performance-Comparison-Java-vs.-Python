import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class BenchmarkRunner {

    // Cyclic Sort implementation matching the repository snippet
    public static int[] cycleSort(int[] arr) {
        int n = arr.length;
        int i = 0;
        while (i < n) {
            int correctpos = arr[i] - 1;
            if (arr[i] != arr[correctpos]) {
                int temp = arr[i];
                arr[i] = arr[correctpos];
                arr[correctpos] = temp;
            } else {
                i++;
            }
        }
        return arr;
    }

    // Dataset generator strictly returning permutations of 1 to N
    public static int[] generateDataset(int size, String charType, long seed) {
        Random random = new Random(seed);
        int[] arr = new int[size];

        if (charType.equals("random")) {
            for (int i = 0; i < size; i++) {
                arr[i] = i + 1;
            }
            // Fisher-Yates shuffle
            for (int i = size - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        } else if (charType.equals("reversed")) {
            for (int i = 0; i < size; i++) {
                arr[i] = size - i;
            }
        } else if (charType.equals("nearly_sorted")) {
            for (int i = 0; i < size; i++) {
                arr[i] = i + 1;
            }
            int swaps = Math.max(1, (int) (size * 0.05));
            for (int s = 0; s < swaps; s++) {
                int idx1 = random.nextInt(size);
                int idx2 = random.nextInt(size);
                int temp = arr[idx1];
                arr[idx1] = arr[idx2];
                arr[idx2] = temp;
            }
        } else {
            throw new IllegalArgumentException("Unknown characteristic type: " + charType);
        }

        return arr;
    }

    public static void main(String[] args) {
        int[] sizes = {100000, 500000, 1000000, 2000000};
        String[] characteristics = {"random", "reversed", "nearly_sorted"};
        int repetitions = 30;
        int warmups = 5;

        String csvFileName = "java_results.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName))) {
            writer.println("Language,Size,Characteristic,Repetition,Time_ms");

            System.out.println("Running Java Benchmarks...");

            for (int size : sizes) {
                for (String charType : characteristics) {
                    // Warm-up phase (5 runs) to trigger JVM JIT compilation
                    for (int w = 0; w < warmups; w++) {
                        int[] warmupData = generateDataset(size, charType, 42);
                        cycleSort(warmupData);
                    }

                    // Measured phase (30 repetitions)
                    for (int rep = 0; rep < repetitions; rep++) {
                        int[] data = generateDataset(size, charType, 42 + rep);

                        long startTime = System.nanoTime();
                        cycleSort(data);
                        long endTime = System.nanoTime();

                        double durationMs = (endTime - startTime) / 1e6;

                        writer.printf("Java,%d,%s,%d,%.6f%n", size, charType, rep + 1, durationMs);
                    }
                }
            }

            System.out.println("Java execution complete! Results saved to " + csvFileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}