package com.datastax.workshop.controller;

import com.datastax.workshop.enity.error.CustomError;
import com.datastax.workshop.enity.killrvideo.UserCredentials;
import com.datastax.workshop.enity.killrvideo.UserCredentialsEntity;
import com.datastax.workshop.enity.todo.Todo;
import com.datastax.workshop.enity.todo.TodoEntity;
import com.datastax.workshop.service.UserCredentialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(
        methods = {POST, GET, OPTIONS, PUT, DELETE, PATCH},
        maxAge = 3600,
        allowedHeaders = {"x-requested-with", "origin", "content-type", "accept"},
        origins = "*"
)
public class UserCredentialsRestController {

    @Autowired
    UserCredentialService service;

    @GetMapping
    @Operation(summary = "Show all the user-crednetials...why not?",
            description = "Show all the user-crednetials...why not?",
            tags = {"list", "fetch", "get", "credentials"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All items retrieved successfuly",
                    content = @Content(schema = @Schema(implementation = UserCredentialsEntity.class))),
            @ApiResponse(responseCode = "400", description = "Something happened"),
            @ApiResponse(responseCode = "500", description = "Server gone crazy  -  error happened",
                    content = @Content(schema = @Schema(implementation = CustomError.class)))})
    List<UserCredentialsEntity> getAll(){
        return service.getAll();
    }

    @PostMapping
    @Operation(summary = "Add a new user + credentials as well",
            description = "Add user",
            tags = { "create", "credentials"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK Boss - mission complete successfully!",
                    content = @Content(schema = @Schema(implementation = UserCredentialsEntity.class))),
            @ApiResponse(responseCode = "400", description = "Something happened"),
            @ApiResponse(responseCode = "500", description = "Server gone crazy  -  error happened",
                    content = @Content(schema = @Schema(implementation = CustomError.class)))})
    UserCredentials add(HttpServletRequest req,
                                 @RequestBody UserCredentials uc){
        return service.addNew(uc);
    }
}
