package org.example.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.TodoItem;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class TodoJsonRepository implements JsonRepository<TodoItem> {

    private final Path filePath;
    private final Gson gson;
    private final Type listType = new TypeToken<List<TodoItem>>() {}.getType();

    public TodoJsonRepository(String filePath) {
        this.filePath = Path.of(filePath);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void save(TodoItem item) throws IOException {
        List<TodoItem> items = findAll();
        items.add(item);
        writeToFile(items);
    }

    @Override
    public void saveAll(List<TodoItem> items) throws IOException {
        List<TodoItem> existing = findAll();
        existing.addAll(items);
        writeToFile(existing);
    }

    @Override
    public List<TodoItem> findAll() throws IOException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        try (Reader reader = Files.newBufferedReader(filePath)) {
            List<TodoItem> items = gson.fromJson(reader, listType);
            return items != null ? items : new ArrayList<>();
        }
    }

    @Override
    public void clear() throws IOException {
        writeToFile(new ArrayList<>());
    }

    private void writeToFile(List<TodoItem> items) throws IOException {
        if (filePath.getParent() != null) {
            Files.createDirectories(filePath.getParent());
        }
        try (Writer writer = Files.newBufferedWriter(filePath)) {
            gson.toJson(items, writer);
        }
    }

    public Path getFilePath() {
        return filePath;
    }
}
