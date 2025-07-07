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
        return todoRepository.save(todo);
    }
    public Todo deleteTodo(Long id){
        Todo todo = todoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Todo not found"));
        todoRepository.deleteById(id);
        return todo;
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        existing.title = updatedTodo.title;
        existing.isDone = updatedTodo.isDone;

        return todoRepository.save(existing);
    }


}
