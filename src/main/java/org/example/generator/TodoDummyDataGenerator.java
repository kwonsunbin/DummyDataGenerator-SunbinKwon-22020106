package org.example.generator;

import org.example.model.TodoItem;
import org.example.model.TodoPriority;
import org.example.model.TodoStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TodoDummyDataGenerator {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final List<String> TITLES = List.of(
            "Fix login page bug",
            "Write unit tests for user service",
            "Update API documentation",
            "Refactor database connection pool",
            "Add pagination to product list",
            "Design onboarding flow",
            "Set up CI/CD pipeline",
            "Migrate to PostgreSQL",
            "Implement OAuth2 login",
            "Add dark mode support",
            "Optimize image loading",
            "Write release notes for v2.0",
            "Code review for payment module",
            "Set up error monitoring",
            "Create admin dashboard",
            "Implement push notifications",
            "Fix memory leak in worker thread",
            "Add CSV export feature",
            "Update privacy policy page",
            "Improve search performance"
    );

    private static final List<String> DESCRIPTIONS = List.of(
            "Needs to be done before the next release.",
            "Should cover all edge cases and error scenarios.",
            "Make sure to follow the existing code style.",
            "Performance impact must be measured before merging.",
            "Coordinate with the design team for final approval.",
            "Write tests alongside the implementation.",
            "Verify compatibility with all supported browsers.",
            "Update the changelog after completion.",
            "Check with QA for acceptance criteria.",
            "Discuss with the team before starting."
    );

    private static final List<String> ASSIGNEES = List.of(
            "alice", "bob", "carol", "dave", "eve",
            "frank", "grace", "henry", "iris", "james"
    );

    private static final List<String> TAG_POOL = List.of(
            "backend", "frontend", "bugfix", "feature", "refactor",
            "testing", "devops", "design", "documentation", "urgent",
            "database", "security", "performance", "ui", "api"
    );

    private final Random random;

    public TodoDummyDataGenerator() {
        this.random = new Random();
    }

    public TodoDummyDataGenerator(long seed) {
        this.random = new Random(seed);
    }

    public TodoItem generate() {
        LocalDate createdAt = LocalDate.now().minusDays(random.nextInt(60));
        LocalDate dueDate = createdAt.plusDays(random.nextInt(30) + 1);

        return TodoItem.builder()
                .id(generateId())
                .title(pick(TITLES))
                .description(pick(DESCRIPTIONS))
                .status(randomEnum(TodoStatus.values()))
                .priority(randomEnum(TodoPriority.values()))
                .createdAt(createdAt.format(DATE_FORMAT))
                .dueDate(dueDate.format(DATE_FORMAT))
                .tags(pickMultiple(TAG_POOL, 1, 3))
                .assignee(pick(ASSIGNEES))
                .build();
    }

    public List<TodoItem> generate(int count) {
        List<TodoItem> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            items.add(generate());
        }
        return items;
    }

    private String generateId() {
        return "todo-" + String.format("%08x", random.nextInt());
    }

    private <T> T pick(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    private <T> T randomEnum(T[] values) {
        return values[random.nextInt(values.length)];
    }

    private List<String> pickMultiple(List<String> pool, int min, int max) {
        int count = min + random.nextInt(max - min + 1);
        List<String> shuffled = new ArrayList<>(pool);
        Collections.shuffle(shuffled, random);
        return shuffled.subList(0, Math.min(count, shuffled.size()));
    }
}
