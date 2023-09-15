package com.springbootmongodbtutorial.controller;

import com.springbootmongodbtutorial.model.TodoDTO;
import com.springbootmongodbtutorial.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
