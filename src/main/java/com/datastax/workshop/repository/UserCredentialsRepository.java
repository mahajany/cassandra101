package com.datastax.workshop.repository;

import com.datastax.workshop.enity.killrvideo.UserCredentialsEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserCredentialsRepository extends CassandraRepository<UserCredentialsEntity, UUID> {
}
