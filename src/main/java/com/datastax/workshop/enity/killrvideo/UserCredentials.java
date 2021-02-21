package com.datastax.workshop.enity.killrvideo;


import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserCredentials {
    private UUID   uuid;
    private String email;
    private String password;

    public UserCredentials(String email, String password){
        this.uuid = UUID.randomUUID();
        this.email=email;
        this.password=password;
    }

}
