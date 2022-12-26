package ru.spbu.apcyb.svp.tasks;

import java.util.ArrayList;
import java.util.List;

public class Task4Benchmark {
    public static void main(String[] args) {
        benchmarkTans(1);
        benchmarkTans(100);
        benchmarkTans(1_000_000);
        benchmarkTans(10_000_000);
    }

    private static void benchmarkTans(int count) {
        var values = generateValues(count);

        double time = benchmark(() -> Task4.calculateTangents(values));
        double timeMultithread = benchmark(() -> Task4.calculateTangentsMultithread(values));

        System.out.println("Calculating " + count + " tangents took");
        System.out.println(" - " + time + "µs with one thread");
        System.out.println(" - " + timeMultithread + "µs with multiple threads");
    }

    private static double benchmark(Runnable fn) {
        long start = System.nanoTime();
        fn.run();
        long end = System.nanoTime();
        return (end - start) / 1000.0; // Время в µs
    }

    private static List<Double> generateValues(int count) {
        var result = new ArrayList<Double>(count);
        for (int i = 0; i < count; i++) {
            result.add(Math.random() * 1000);
        }
        return result;
    }
}
