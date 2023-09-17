package com.springbootmongodbtutorial.service;

import com.springbootmongodbtutorial.exception.TodoCollectionException;
import com.springbootmongodbtutorial.model.TodoDTO;

public interface TodoService {
    TodoDTO createNewToDo(final TodoDTO todoDTO) throws TodoCollectionException;
}
