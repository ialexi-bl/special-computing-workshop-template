package ru.spbu.apcyb.svp.tasks;

import java.io.IOException;
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

        if (args.length < 1) {
            logger.info("Usage: Task3 [directory]");
            return;
        }

        try {
            FileTree tree = getFileTree(args[0]);
            logger.info(tree.toString());
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Рекурсивно строит дерево папок и файлов по переданному пути.
     */
    public static FileTree getFileTree(String path) throws IOException {
        Path dir = Path.of(path);

        if (!Files.isDirectory(dir)) {
            throw new IOException("\"" + path + "\" is not a directory");
        }

        return traverseDirectory(dir);
    }

    private static FileTree traverseDirectory(Path dir) {
        HashMap<String, FileTree> directories = new HashMap<>();
        List<String> files = new ArrayList<>();

        try (var stream = Files.list(dir)) {
            stream.forEach(path -> {
                if (Files.isDirectory(path)) {
                    directories.put(path.getFileName().toString(), traverseDirectory(path));
                } else {
                    files.add(path.getFileName().toString());
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return new FileTree(directories, files);
    }

    private record FileTree(
        HashMap<String, FileTree> directories,
        List<String> files
    ) {

        public String toString() {
            return toString(0);
        }

        private String toString(int indentSize) {
            StringBuilder result = new StringBuilder();
            String indent = "│ ".repeat(indentSize);
            for (var file : files) {
                result
                    .append(indent)
                    .append("├ ")
                    .append(file)
                    .append("\n");
            }
            for (var entry : directories.entrySet()) {
                result
                    .append(indent)
                    .append("├ ")
                    .append(entry.getKey())
                    .append("\n")
                    .append(entry.getValue().toString(indentSize + 1));
            }
            return result.toString();
        }
    }
}
