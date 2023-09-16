package com.springbootmongodbtutorial.controller;

import com.springbootmongodbtutorial.model.TodoDTO;
import com.springbootmongodbtutorial.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class TodoController {
    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodo() {
        List<TodoDTO> todos = todoRepository.findAll();

        if (todos.size() > 0) {
            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
        } else return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) {
        try {
            todoDTO.setCreateAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoDTO);
            return new ResponseEntity<>(todoDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getTodo(@PathVariable String id) {
        Optional<TodoDTO> todoDTO = todoRepository.findById(id);
        if (todoDTO.isPresent()) {
            return new ResponseEntity<>(todoDTO, HttpStatus.OK);
        } else
            return new ResponseEntity<>("Todo not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id,
                                        @Validated @RequestBody TodoDTO todoDTO) {
        Optional<TodoDTO> todo = todoRepository.findById(id);

        return todo.map(existingTodo -> {
            existingTodo.setTodo(todoDTO.getTodo() != null ? todoDTO.getTodo() : existingTodo.getTodo());
            existingTodo.setCompleted(todoDTO.getCompleted() != null ? todoDTO.getCompleted() : existingTodo.getCompleted());
            existingTodo.setDescription(todoDTO.getDescription() != null ? todoDTO.getDescription() : existingTodo.getDescription());
            existingTodo.setUpdateAt(new Date(System.currentTimeMillis()));

            todoRepository.save(existingTodo);
            return ResponseEntity.ok(existingTodo);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found with id: " + id));
    }
}
