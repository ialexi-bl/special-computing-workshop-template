package ru.spbu.apcyb.svp.tasks;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Тесты для задания 5.
 */
public class Task5Test {
    private static final Path TEST_FILE_PATH = Path.of("./src/test/resources/task-5/test.txt");
    private static final Path OUTPUT_PATH = Path.of("./src/test/resources/task-5/output");
    private static final Path OUTPUT_FILE_PATH = Path.of("./src/test/resources/task-5/output.txt");

    @AfterAll
    static void cleanOutput() {
        try {
            Files.deleteIfExists(OUTPUT_FILE_PATH);

            Files.list(OUTPUT_PATH).forEach(file -> {
                try {
                    Files.delete(file);
                } catch (IOException ignored) {
                }
            });
        } catch (IOException ignored) {
        }

    }

    @Test
    void getWordCounts() throws IOException {
        var result = Task5.getWordCounts(TEST_FILE_PATH.toString());

        Assertions.assertEquals(
                Map.ofEntries(
                        Map.entry("foo", 4),
                        Map.entry("bar", 2)
                ),
                result,
                "Wrong word counts"
        );
    }

    @Test
    void writeWordCount() throws IOException {
        Task5.writeWordCount(OUTPUT_FILE_PATH.toString(), Map.ofEntries(
                Map.entry("foo", 4),
                Map.entry("bar", 2)
        ));

        Assertions.assertEquals(
                Set.of("foo 4", "bar 2"),
                new HashSet<>(Files.readAllLines(OUTPUT_FILE_PATH)),
                "Wrong content"
        );
    }

    @Test
    void writeWordFiles() throws IOException {
        Task5.writeWordFiles(OUTPUT_PATH.toString(), Map.ofEntries(
                Map.entry("foo", 4),
                Map.entry("bar", 2)
        ));

        try (var stream = Files.list(OUTPUT_PATH)) {
            var fileNames = stream.map(Path::getFileName).map(Path::toString).collect(Collectors.toSet());

            Assertions.assertEquals(
                    Set.of("foo.txt", "bar.txt"),
                    fileNames,
                    "Wrong files were created"
            );
        }

        Assertions.assertIterableEquals(
                List.of("foo foo foo foo "),
                Files.readAllLines(OUTPUT_PATH.resolve("foo.txt")),
                "Wrong contents in foo.txt"
        );
        Assertions.assertIterableEquals(
                List.of("bar bar "),
                Files.readAllLines(OUTPUT_PATH.resolve("bar.txt")),
                "Wrong contents in bar.txt"
        );
    }
}
