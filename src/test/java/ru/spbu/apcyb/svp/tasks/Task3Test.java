package ru.spbu.apcyb.svp.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Тесты для задания 3.
 */
public class Task3Test {
    private final String testDirectoryPath = "./src/test/resources/task-2/test-dir";
    private final String testDirectoryExpectedOutput = "./src/test/resources/task-2/expected.txt";
    private final String testOutputPath = "./src/test/resources/output.txt";

    @Test
    public void writing() throws IOException {
        Task3.writeToFile(testOutputPath, "foobar");
        Assertions.assertEquals(
                Files.readString(Path.of(testOutputPath), Charset.defaultCharset()),
                "foobar",
                "Wrong value in file"
        );
    }

    @Test
    public void reading() throws IOException {
        var tree = Task3.getFileTree(testDirectoryPath);
        Assertions.assertEquals(
                tree.toString(),
                Files.readString(Path.of(testDirectoryExpectedOutput), Charset.defaultCharset()),
                "Wrong tree"
        );
    }

    @Test
    public void readingNonDirectory() {
        Assertions.assertThrows(
                IOException.class,
                () -> Task3.getFileTree(testDirectoryExpectedOutput),
                "Should not be able to get tree from file path"
        );
    }

    @Test
    public void writingNonFile() {
        Assertions.assertThrows(
                IOException.class,
                () -> Task3.writeToFile(testDirectoryPath, "foobar"),
                "Should not be able to write to non-file path"
        );
    }
}
