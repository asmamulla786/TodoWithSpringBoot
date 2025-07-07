package com.example.mytodo.controller;

import com.example.mytodo.model.Todo;
import com.example.mytodo.repository.TodoRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    private final TodoRepository todoRepository;
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/todos")
    public List<Todo> getTodos(){
        return todoRepository.findAll();
    }

    @PostMapping("/todos")
    public Todo postTodos(@Valid @RequestBody Todo todo){
        System.out.println("todo "+todo);
        todoRepository.save(todo);
        return todo;
    }
    @DeleteMapping("/todos/{id}")
    public String deleteTodos(@PathVariable Long id){
        todoRepository.deleteById(id);
        return "deleted" + id + "todo";
    }
}
