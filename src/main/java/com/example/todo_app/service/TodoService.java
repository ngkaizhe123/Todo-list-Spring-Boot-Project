package com.example.todo_app.service;

import com.example.todo_app.repository.TodoRepository;
import com.example.todo_app.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    // get all todo list
    public List<Todo> getAllTodos() {
        return todoRepository.findByOrderByCreatedAtDesc();
    }

    // base id get the todo
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    // save the todo
    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    // delete todo
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    // change to complete status
    public Todo toggleComplete(Long id) {
        Optional<Todo> todoOpt = todoRepository.findById(id);
        if (todoOpt.isPresent()) {
            Todo todo = todoOpt.get();
            todo.setCompleted(!todo.isCompleted());
            return todoRepository.save(todo);
        }
        return null;
    }
}
