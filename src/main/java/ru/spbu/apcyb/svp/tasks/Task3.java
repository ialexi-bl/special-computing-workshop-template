package ru.spbu.apcyb.svp.tasks;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Задание 3.
 */
public class Task3 {

    private static final Logger logger = LogManager.getLogger(Task3.class);

    public static void main(String[] args) {
        Configurator.setLevel(logger, Level.INFO);

        if (args.length < 2) {
            logger.info("Usage: Task3 [directory] [output]");
            return;
        }

        try {
            FileTree tree = getFileTree(args[0]);
            writeToFile(args[1], tree.toString());
            logger.info("Done");
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static void writeToFile(String path, String value) throws IOException {
        Path file = Path.of(path);

        if(Files.exists(file) && !Files.isRegularFile(file)) {
            throw new IOException("\"" + file + "\" is not a file");
        }

        Files.write(file, value.getBytes());
    }

    /**
     * Рекурсивно строит дерево папок и файлов по переданному пути.
     */
    public static FileTree getFileTree(String path) throws IOException, UncheckedIOException {
        Path dir = Path.of(path);

        if (!Files.isDirectory(dir)) {
            throw new IOException("\"" + path + "\" is not a directory");
        }

        return traverseDirectory(dir);
    }

    private static FileTree traverseDirectory(Path dir) {
        List<FileTree> directories = new ArrayList<>();
        List<String> files = new ArrayList<>();

        try (var stream = Files.list(dir)) {
            stream.forEach(path -> {
                if (Files.isDirectory(path)) {
                    directories.add(traverseDirectory(path));
                } else {
                    files.add(path.getFileName().toString());
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return new FileTree(dir.getFileName().toString(), directories, files);
    }

    public record FileTree(
        String dirName,
        List<FileTree> directories,
        List<String> files
    ) {

        public String toString() {
            return toString(0);
        }

        private String toString(int indentSize) {
            StringBuilder result = new StringBuilder();
            String indent = "│ ".repeat(indentSize);

            result
                    .append(dirName)
                    .append("\n");

            for (var file : files) {
                result
                    .append(indent)
                    .append("├ ")
                    .append(file)
                    .append("\n");
            }

            for (var entry : directories) {
                result
                    .append(indent)
                    .append("├ ")
                    .append(entry.toString(indentSize + 1));
            }

            return result.toString();
        }
    }
}
