package org.example.model;

import java.time.LocalDate;
import java.util.List;

public class TodoItem {

    private final String id;
    private final String title;
    private final String description;
    private final TodoStatus status;
    private final TodoPriority priority;
    private final String createdAt;
    private final String dueDate;
    private final List<String> tags;
    private final String assignee;

    private TodoItem(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.status = builder.status;
        this.priority = builder.priority;
        this.createdAt = builder.createdAt;
        this.dueDate = builder.dueDate;
        this.tags = builder.tags;
        this.assignee = builder.assignee;
    }

    public String getId()           { return id; }
    public String getTitle()        { return title; }
    public String getDescription()  { return description; }
    public TodoStatus getStatus()   { return status; }
    public TodoPriority getPriority() { return priority; }
    public String getCreatedAt()    { return createdAt; }
    public String getDueDate()      { return dueDate; }
    public List<String> getTags()   { return tags; }
    public String getAssignee()     { return assignee; }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s / %s) → %s", id, title, status, priority, dueDate);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String title;
        private String description;
        private TodoStatus status;
        private TodoPriority priority;
        private String createdAt;
        private String dueDate;
        private List<String> tags;
        private String assignee;

        public Builder id(String id)                    { this.id = id; return this; }
        public Builder title(String title)              { this.title = title; return this; }
        public Builder description(String description)  { this.description = description; return this; }
        public Builder status(TodoStatus status)        { this.status = status; return this; }
        public Builder priority(TodoPriority priority)  { this.priority = priority; return this; }
        public Builder createdAt(String createdAt)      { this.createdAt = createdAt; return this; }
        public Builder dueDate(String dueDate)          { this.dueDate = dueDate; return this; }
        public Builder tags(List<String> tags)          { this.tags = tags; return this; }
        public Builder assignee(String assignee)        { this.assignee = assignee; return this; }

        public TodoItem build() {
            return new TodoItem(this);
        }
    }
}
