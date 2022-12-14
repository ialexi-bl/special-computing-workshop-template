package ru.spbu.apcyb.svp.tasks;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Задание 4.
 */
public class Task4 {
    private static final Logger logger = LogManager.getLogger(Task3.class);

    public static void main(String[] args) {
        Configurator.setLevel(logger, Level.INFO);

        if (args.length != 1) {
            logger.info("Usage: Task3 [path]");
            return;
        }

        try {
            var values = readValues(args[0]);
            var tans = calculateTangents(values);
            logger.info(tans);

            var tansd = calculateTangentsMultithread(values);
            logger.info(tansd);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static List<Double> readValues(String path) throws IOException {
        var file = Path.of(path);
        var result = new ArrayList<Double>();
        var scanner = new Scanner(file);

        while (scanner.hasNextDouble()) {
            result.add(scanner.nextDouble());
        }

        if (scanner.hasNext()) {
            throw new IllegalArgumentException("File may contain only double numbers separated by spaces");
        }

        return result;
    }

    public static List<Double> calculateTangents(final List<Double> values) {
        var result = new Double[values.size()];
        int s = values.size();

        for (int j = 0; j < s; j++) {
            result[j] = Math.tan(values.get(j));
        }

        return Arrays.asList(result);
    }

    public static List<Double> calculateTangentsMultithread(final List<Double> values) {
        final int threadsCount = 10;
        var executor = Executors.newFixedThreadPool(threadsCount);
        var result = new Double[values.size()];

        int s = values.size();
        int step = (int) Math.ceil((double) s / (double) threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            int threadIndex = i;
            executor.execute(() -> {
                int from = step * threadIndex;
                int to = Math.min(step * (threadIndex + 1), s);
                for (int j = from; j < to; j++) {
                    result[j] = Math.tan(values.get(j));
                }
            });
        }

        try {
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        }

        return Arrays.asList(result);
    }
}
