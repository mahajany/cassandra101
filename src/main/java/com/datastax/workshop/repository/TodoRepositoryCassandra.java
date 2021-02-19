package com.datastax.workshop.repository;

import com.datastax.workshop.enity.todo.TodoEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TodoRepositoryCassandra
        extends CassandraRepository<TodoEntity, UUID> {}
