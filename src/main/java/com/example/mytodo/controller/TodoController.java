package com.example.mytodo.controller;

import com.example.mytodo.model.Todo;
import com.example.mytodo.repository.TodoRepository;
import com.example.mytodo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    private TodoService todoService;
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public List<Todo> getTodos(){
        return todoService.getTodos();
    }

    @PostMapping("/todos")
    public Todo addTodo(@Valid @RequestBody Todo todo){
        return todoService.addTodo(todo);
    }

    @DeleteMapping("/todos/{id}")
    public String deleteTodo(@PathVariable Long id){
        return todoService.deleteTodo(id);
    }

    @PutMapping("/todos/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        return todoService.updateTodo(id, updatedTodo);
    }

}
