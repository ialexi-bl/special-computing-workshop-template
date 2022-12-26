package ru.spbu.apcyb.svp.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Тесты для задания 4.
 */
public class Task4Test {
    final String testDirectory = "./src/test/resources/task-3";
    final String testOutputPath = testDirectory + "/output.txt";
    final String testInputPath = testDirectory + "/values-10.txt";

    @Test
    void readFile() throws IOException {
        var values = Task4.readValues(testInputPath);
        Assertions.assertIterableEquals(values, List.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
    }

    @Test
    void calculateTans() {
        var values = List.of(0.0, 0.78539, 2.35619, 3.14159, 100.0);
        var expected = List.of(0.0, 1.0, -1.0, 0.0, -0.58721);
        var tans = Task4.calculateTangents(values);

        Assertions.assertEquals(tans.size(), expected.size(), "Wrong result length");
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i), tans.get(i), 1e-4, "Wrong value at index " + i);
        }
    }

    @Test
    void calculateTansMultithread() {
        var values = List.of(0.0, 0.78539, 2.35619, 3.14159, 100.0);
        var expected = List.of(0.0, 1.0, -1.0, 0.0, -0.58721);
        var tans = Task4.calculateTangentsMultithread(values);

        Assertions.assertEquals(tans.size(), expected.size(), "Wrong result length");
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i), tans.get(i), 1e-4, "Wrong value at index " + i);
        }
    }

    @Test
    public void writing() throws IOException {
        Task4.writeToFile(testOutputPath, List.of(0.0, 1.0, 2.1));
        Assertions.assertEquals(
                "0.0 1.0 2.1",
                Files.readString(Path.of(testOutputPath), Charset.defaultCharset()),
                "Wrong value in file"
        );
    }

    @Test
    public void writingNonFile() {
        Assertions.assertThrows(
                IOException.class,
                () -> Task4.writeToFile(testDirectory, List.of(1.0)),
                "Should not be able to write to non-file path"
        );
    }
}
