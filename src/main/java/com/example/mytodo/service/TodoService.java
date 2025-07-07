package com.example.mytodo.service;

import com.example.mytodo.model.Todo;
import com.example.mytodo.repository.TodoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    public List<Todo> getTodos(){
        return todoRepository.findAll();
    }

    public Todo addTodo(@Valid @RequestBody Todo todo){
        todoRepository.save(todo);
        return todo;
    }
    public String deleteTodo(@PathVariable Long id){
        todoRepository.deleteById(id);
        return "deleted " + id + " todo";
    }
}
