package org.example;

import org.example.generator.TodoDummyDataGenerator;
import org.example.model.TodoItem;
import org.example.repository.TodoJsonRepository;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        int count = parseCount(args);
        String outputPath = parseOutputPath(args);

        System.out.println("=== Todo Dummy Data Generator ===");
        System.out.printf("Generating %d todo items → %s%n%n", count, outputPath);

        TodoDummyDataGenerator generator = new TodoDummyDataGenerator();
        TodoJsonRepository repository = new TodoJsonRepository(outputPath);

        repository.clear();

        List<TodoItem> todos = generator.generate(count);
        repository.saveAll(todos);

        System.out.println("Generated items:");
        todos.forEach(item -> System.out.println("  " + item));

        System.out.printf("%nDone. %d items saved to %s%n",
                todos.size(), repository.getFilePath().toAbsolutePath());
    }

    private static int parseCount(String[] args) {
        if (args.length >= 1) {
            try { return Integer.parseInt(args[0]); } catch (NumberFormatException ignored) {}
        }
        return 10;
    }

    private static String parseOutputPath(String[] args) {
        return args.length >= 2 ? args[1] : "output/todos.json";
    }
}
