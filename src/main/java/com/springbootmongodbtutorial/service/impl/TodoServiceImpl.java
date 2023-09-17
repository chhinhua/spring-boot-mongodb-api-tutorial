package com.springbootmongodbtutorial.service.impl;

import com.springbootmongodbtutorial.exception.TodoCollectionException;
import com.springbootmongodbtutorial.model.TodoDTO;
import com.springbootmongodbtutorial.repository.TodoRepository;
import com.springbootmongodbtutorial.service.TodoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoDTO createNewToDo(TodoDTO todoDTO) throws TodoCollectionException {
        Optional<TodoDTO> existingTodo = todoRepository.findByTodo(todoDTO.getTodo());

        if (existingTodo.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
        }

        if (todoDTO.getCompleted() == null) {
            todoDTO.setCompleted(false);
        }
        todoDTO.setCreateAt(new Date());
        todoRepository.save(todoDTO);

        return todoDTO;
    }
}
