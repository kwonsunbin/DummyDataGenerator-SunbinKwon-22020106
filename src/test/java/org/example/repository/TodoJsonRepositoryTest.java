package org.example.repository;

import org.example.generator.TodoDummyDataGenerator;
import org.example.model.TodoItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoJsonRepositoryTest {

    @TempDir
    Path tempDir;

    private TodoJsonRepository repository;
    private TodoDummyDataGenerator generator;

    @BeforeEach
    void setUp() {
        repository = new TodoJsonRepository(tempDir.resolve("todos.json").toString());
        generator = new TodoDummyDataGenerator(42L);
    }

    @Test
    void findAll_emptyWhenFileDoesNotExist() throws IOException {
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void save_singleItem_canBeReadBack() throws IOException {
        TodoItem item = generator.generate();
        repository.save(item);

        List<TodoItem> found = repository.findAll();
        assertEquals(1, found.size());
        assertEquals(item.getId(), found.get(0).getId());
        assertEquals(item.getTitle(), found.get(0).getTitle());
    }

    @Test
    void saveAll_multipleItems_allPersistedCorrectly() throws IOException {
        List<TodoItem> items = generator.generate(5);
        repository.saveAll(items);

        List<TodoItem> found = repository.findAll();
        assertEquals(5, found.size());
    }

    @Test
    void saveAll_appendsToExistingItems() throws IOException {
        repository.saveAll(generator.generate(3));
        repository.saveAll(generator.generate(2));

        assertEquals(5, repository.findAll().size());
    }

    @Test
    void clear_removesAllItems() throws IOException {
        repository.saveAll(generator.generate(5));
        repository.clear();

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void save_preservesAllFields() throws IOException {
        TodoItem original = generator.generate();
        repository.save(original);

        TodoItem loaded = repository.findAll().get(0);
        assertEquals(original.getId(), loaded.getId());
        assertEquals(original.getTitle(), loaded.getTitle());
        assertEquals(original.getStatus(), loaded.getStatus());
        assertEquals(original.getPriority(), loaded.getPriority());
        assertEquals(original.getCreatedAt(), loaded.getCreatedAt());
        assertEquals(original.getDueDate(), loaded.getDueDate());
        assertEquals(original.getAssignee(), loaded.getAssignee());
        assertEquals(original.getTags(), loaded.getTags());
    }
}
