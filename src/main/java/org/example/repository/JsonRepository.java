package org.example.repository;

import java.io.IOException;
import java.util.List;

public interface JsonRepository<T> {

    void save(T item) throws IOException;

    void saveAll(List<T> items) throws IOException;

    List<T> findAll() throws IOException;

    void clear() throws IOException;
}
