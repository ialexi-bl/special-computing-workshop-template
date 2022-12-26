package ru.spbu.apcyb.svp.tasks;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * Задание 5.
 */
public class Task5 {
    private static final Logger logger = LogManager.getLogger(Task5.class);

    /**
     * Main.
     */
    public static void main(String[] args) {
        Configurator.setLevel(logger, Level.INFO);

        if (args.length != 3) {
            logger.info("Usage: Task5 [input path] [count output path] [words output directory]");
            return;
        }

        try {
            FileUtils.cleanDirectory(new File(args[2]));

            var counts = getWordCounts(args[0]);
            writeWordCount(args[1], counts);
            writeWordFiles(args[2], counts);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Counts occurrences of each word in a file.
     */
    public static Map<String, Integer> getWordCounts(String path) throws IOException {
        var result = new HashMap<String, Integer>();

        final Consumer<String> processWord = word -> {
            if (word.length() == 0) {
                return;
            }
            if (!result.containsKey(word)) {
                result.put(word, 1);
            } else {
                result.put(word, result.get(word) + 1);
            }
        };
        final Consumer<String> processLine = line -> {
            Pattern
                    .compile("[^\\p{L}-]+")
                    .splitAsStream(line)
                    .forEach(processWord);
        };

        try (var stream = Files.lines(Path.of(path))) {
            stream.forEach(processLine);
        }

        return result;
    }

    /**
     * Writes the number of occurrences of each word to a file.
     */
    public static void writeWordCount(String path, Map<String, Integer> counts) throws IOException {
        try (var stream = Files.newOutputStream(Path.of(path))) {
            for (var entry : counts.entrySet()) {
                stream.write(entry.getKey().getBytes());
                stream.write(" ".getBytes());
                stream.write(entry.getValue().toString().getBytes());
                stream.write("\n".getBytes());
            }
        }
    }

    /**
     * Creates a file for each word with this word repeated
     * the number of times specified in `counts` map.
     */
    public static void writeWordFiles(String path, Map<String, Integer> counts) {
        var dir = Path.of(path);
        if (!Files.isDirectory(dir)) {
            throw new IllegalArgumentException("Path is not a directory");
        }

        try (var executor = Executors.newFixedThreadPool(10)) {
            for (var entry : counts.entrySet()) {
                var word = entry.getKey();
                var count = entry.getValue();
                var file = dir.resolve(word + ".txt");

                CompletableFuture.runAsync(() -> {
                    try (var stream = Files.newOutputStream(file)) {
                        var bytes = word.getBytes();
                        for (int i = 0; i < count; i++) {
                            stream.write(bytes);
                            stream.write(" ".getBytes());
                        }
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }, executor);
            }
        }
    }
}
