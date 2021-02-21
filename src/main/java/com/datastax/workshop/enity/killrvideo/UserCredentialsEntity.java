package com.datastax.workshop.enity.killrvideo;

import com.datastax.workshop.enity.todo.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = UserCredentialsEntity.TABLE_NAME)
public class UserCredentialsEntity {

    public static final String TABLE_NAME    = "user_credentials" ;
    public static final String USERID       = "userid";
    public static final String EMAIL        = "email";
    public static final String PASSWORD     = "password";


    @PrimaryKey
    @Column(USERID)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID userid;

    @Column(EMAIL)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String email;

    @Column(PASSWORD)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String password;

}
