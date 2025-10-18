package com.chandan.task;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    // Custom query to find tasks by name (case-insensitive)
    List<Task> findByNameContaining(String name);
}

