package com.datastax.workshop.controller;

import com.datastax.workshop.repository.TodoRepositoryCassandra;
import com.datastax.workshop.enity.todo.Todo;
import com.datastax.workshop.enity.todo.TodoEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin(
        methods = {POST, GET, OPTIONS, PUT, DELETE, PATCH},
        maxAge = 3600,
        allowedHeaders = {"x-requested-with", "origin", "content-type", "accept"},
        origins = "*"
)
@RequestMapping("/api/v1/todos/")
public class TodoRestController {

    @Autowired
    private TodoRepositoryCassandra repo;

//    private Map<UUID, Todo> todoStore = new ConcurrentHashMap<>();

    @GetMapping
    @Operation(summary = "Show all the todo-tasks",
            description = "GET - list all the tasks you want 'todo'!",
            tags = {"list", "fetch", "get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All items retreived successfuly",
                    content = @Content(schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "400", description = "Something happened"),
            @ApiResponse(responseCode = "500", description = "Some error happened")})

    public Stream<Todo> findAll(HttpServletRequest req) {
//        return todoStore.values().stream().map(t -> t.setUrl(req));

        return repo.findAll().stream()
                .map(TodoRestController::mapAsTodo)
                .map(t -> t.setUrl(req));
    }

    @GetMapping("/{uid}")
    @Operation(summary = "Fetch a particular task",
            description = "GET - one particulr task from the database",
            tags = {"get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "The task has been successfully created",
                    content = @Content(schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "400", description = "Title is blank but is mandatory"),
            @ApiResponse(responseCode = "500", description = "An error occur in storage")})

    public ResponseEntity<Todo> findById(HttpServletRequest req, @PathVariable(value = "uid") String uid) {
//        Todo todo = todoStore.get(UUID.fromString(uid));
//        return (null == todo) ? ResponseEntity.notFound().build() : ResponseEntity.ok(todo.setUrl(req));
        Optional<TodoEntity> e = repo.findById(UUID.fromString(uid));
        if (e.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapAsTodo(e.get()).setUrl(req.getRequestURL().toString()));

    }

    @PostMapping
    @Operation(summary = "Create a new Todo",
            description = "POST is the proper http verb when you cannot provide the full URL (including id)",
            tags = {"create"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "The task has been successfully created",
                    content = @Content(schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "400", description = "Title is blank but is mandatory"),
            @ApiResponse(responseCode = "500", description = "An error occur in storage")})
    public ResponseEntity<Todo> create(HttpServletRequest req,
                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                               description = "Update all fields if needed",
                                               required = true,
                                               content = @Content(schema = @Schema(implementation = Todo.class)))
                                       @RequestBody Todo todoReq)
            throws URISyntaxException {
//        Todo newTodo = new Todo(todoReq.getTitle(), todoReq.getOrder(), todoReq.isCompleted());
//        newTodo.setUrl(req);
//        todoStore.put(newTodo.getUuid(), newTodo);
//        return ResponseEntity.created(new URI(newTodo.getUrl())).body(newTodo);
        TodoEntity te = mapAsTodoEntity(todoReq);
        repo.save(te);
        todoReq.setUuid(te.getUid());
        todoReq.setUrl(req);
        return ResponseEntity.created(new URI(todoReq.getUrl())).body(todoReq);

    }

    @PatchMapping("{uid}")
    @Operation(summary = "Update a task",
            description = "PATCH - update an existing task",
            tags = {"update"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The task has been successfully updated",
                    content = @Content(schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "400", description = "Warning from API"),
            @ApiResponse(responseCode = "500", description = "An error occur in storage")})
    public ResponseEntity<Todo> update(HttpServletRequest req, @PathVariable(value = "uid") String uid, @RequestBody Todo todoReq)
            throws URISyntaxException {
//        if (!todoStore.containsKey(UUID.fromString(uid))) {
//            return ResponseEntity.notFound().build();
//        }
//        Todo existingTodo = todoStore.get(UUID.fromString(uid));
//        if (null != todoReq.getTitle()) {
//            existingTodo.setTitle(todoReq.getTitle());
//        }
//        if (existingTodo.getOrder() != todoReq.getOrder()) {
//            existingTodo.setOrder(todoReq.getOrder());
//        }
//        if (existingTodo.isCompleted() != todoReq.isCompleted()) {
//            existingTodo.setCompleted(todoReq.isCompleted());
//        }
//        return ResponseEntity.accepted().body(existingTodo);

        todoReq.setUuid(UUID.fromString(uid));
        todoReq.setUrl(req.getRequestURL().toString());
        repo.save(mapAsTodoEntity(todoReq));
        return ResponseEntity.accepted().body(todoReq);
    }

    @DeleteMapping("{uid}")
    @Operation(summary = "Delete a task",
            description = "DELETE task which you have completed or never want to see again!",
            tags = {"delete"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The task has been successfully deleted",
                    content = @Content(schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "400", description = "Title is blank but is mandatory"),
            @ApiResponse(responseCode = "500", description = "An error occur in storage")})
    public ResponseEntity<Void> deleteById(@PathVariable(value = "uid") String uid) {
//        if (!todoStore.containsKey(UUID.fromString(uid))) {
//            return ResponseEntity.notFound().build();
//        }
//        todoStore.remove(UUID.fromString(uid));
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        if (!repo.existsById(UUID.fromString(uid))) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(UUID.fromString(uid));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Operation(summary = "Delete everything",
            description = "DELETE task which you have completed or never want to see again!",
            tags = {"delete"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The task has been successfully deleted",
                    content = @Content(schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "400", description = "Title is blank but is mandatory"),
            @ApiResponse(responseCode = "500", description = "An error occur in storage")})
    public ResponseEntity<Void> deleteAll(HttpServletRequest request) {
//        todoStore.clear();
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        repo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private static Todo mapAsTodo(TodoEntity te) {
        Todo todo = new Todo();
        todo.setTitle(te.getTitle());
        todo.setOrder(te.getOrder());
        todo.setUuid(te.getUid());
        todo.setCompleted(te.isCompleted());
        return todo;
    }

    private static TodoEntity mapAsTodoEntity(Todo te) {
        TodoEntity todo = new TodoEntity();
        if (null != te.getUuid()) {
            todo.setUid(te.getUuid());
        } else {
            todo.setUid(UUID.randomUUID());
        }
        todo.setTitle(te.getTitle());
        todo.setOrder(te.getOrder());
        todo.setCompleted(te.isCompleted());
        return todo;
    }
}
