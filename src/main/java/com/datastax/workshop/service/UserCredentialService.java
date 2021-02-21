package com.datastax.workshop.service;

import com.datastax.workshop.enity.killrvideo.UserCredentials;
import com.datastax.workshop.enity.killrvideo.UserCredentialsEntity;
import com.datastax.workshop.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserCredentialService {
    @Autowired
    UserCredentialsRepository repo;

    public List<UserCredentialsEntity> getAll(){
        return repo.findAll();
    }

    public UserCredentials addNew(UserCredentials uc){
        UserCredentialsEntity uce = mapAsUserCredentialsEntity(uc);
        return mapAsUserCredentials(repo.save(uce));
    }


    private static UserCredentials mapAsUserCredentials(UserCredentialsEntity uce) {
        UserCredentials uc = new UserCredentials();
        uc.setUuid(uce.getUserid());
        uc.setEmail(uce.getEmail());
        uc.setPassword(uce.getPassword());
        return uc;
    }

    private static UserCredentialsEntity mapAsUserCredentialsEntity(UserCredentials uc) {
        UserCredentialsEntity uce = new UserCredentialsEntity();
        if (null != uc.getUuid()) {
            uce.setUserid(uc.getUuid());
        } else {
            uce.setUserid(UUID.randomUUID());
        }
        uce.setEmail(uc.getEmail());
        uce.setPassword(uc.getPassword());
        return uce;
    }

}
