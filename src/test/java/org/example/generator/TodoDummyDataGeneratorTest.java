package org.example.generator;

import org.example.model.TodoItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoDummyDataGeneratorTest {

    private TodoDummyDataGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new TodoDummyDataGenerator(42L);
    }

    @Test
    void generateSingleItem_shouldHaveAllFields() {
        TodoItem item = generator.generate();

        assertNotNull(item.getId());
        assertNotNull(item.getTitle());
        assertNotNull(item.getDescription());
        assertNotNull(item.getStatus());
        assertNotNull(item.getPriority());
        assertNotNull(item.getCreatedAt());
        assertNotNull(item.getDueDate());
        assertNotNull(item.getTags());
        assertFalse(item.getTags().isEmpty());
        assertNotNull(item.getAssignee());
    }

    @Test
    void generateMultipleItems_shouldReturnCorrectCount() {
        List<TodoItem> items = generator.generate(20);
        assertEquals(20, items.size());
    }

    @Test
    void generateItems_idsShouldBeUnique() {
        List<TodoItem> items = generator.generate(100);
        long uniqueIds = items.stream().map(TodoItem::getId).distinct().count();
        assertEquals(100, uniqueIds);
    }

    @Test
    void generateItems_dueDateShouldBeAfterCreatedAt() {
        List<TodoItem> items = generator.generate(50);
        items.forEach(item ->
                assertTrue(item.getDueDate().compareTo(item.getCreatedAt()) >= 0,
                        "dueDate must be >= createdAt for item: " + item.getId())
        );
    }

    @Test
    void generateWithSameSeed_shouldProduceSameResults() {
        List<TodoItem> first = new TodoDummyDataGenerator(99L).generate(5);
        List<TodoItem> second = new TodoDummyDataGenerator(99L).generate(5);

        for (int i = 0; i < 5; i++) {
            assertEquals(first.get(i).getId(), second.get(i).getId());
            assertEquals(first.get(i).getTitle(), second.get(i).getTitle());
        }
    }
}
