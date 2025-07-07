package com.example.mytodo.controller;

import com.example.mytodo.model.Todo;
import com.example.mytodo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTodos() throws Exception {
        Todo todo1 = new Todo();
        todo1.id = 1L;
        todo1.title = "todo1";
        todo1.isDone = false;

        Todo todo2 = new Todo();
        todo2.id = 2L;
        todo2.title = "todo2";
        todo2.isDone = true;

        List<Todo> todos = List.of(todo1, todo2);
        Mockito.when(todoService.getTodos()).thenReturn(todos);

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("todo1"))
                .andExpect(jsonPath("$[0].isDone").value(false))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("todo2"))
                .andExpect(jsonPath("$[1].isDone").value(true));
    }

    @Test
    void addTodo() throws Exception {
        Todo input = new Todo();
        input.title = "todo1";
        input.isDone = false;

        Todo saved = new Todo();
        saved.id = 1L;
        saved.title = "todo1";
        saved.isDone = false;

        Mockito.when(todoService.addTodo(any(Todo.class))).thenReturn(saved);

        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("todo1"))
                .andExpect(jsonPath("$.isDone").value(false));
    }

    @Test
    void deleteTodo() throws Exception {
        Long id = 1L;
        Todo deleted = new Todo();
        deleted.id = id;
        deleted.title = "todo1";
        deleted.isDone = false;

        Mockito.when(todoService.deleteTodo(id)).thenReturn(deleted);

        mockMvc.perform(delete("/todos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("todo1"))
                .andExpect(jsonPath("$.isDone").value(false));
    }

    @Test
    void updateTodo() throws Exception {
        Long id = 1L;
        Todo updated = new Todo();
        updated.id = id;
        updated.title = "updated title";
        updated.isDone = true;

        Mockito.when(todoService.updateTodo(eq(id), any(Todo.class))).thenReturn(updated);

        mockMvc.perform(put("/todos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("updated title"))
                .andExpect(jsonPath("$.isDone").value(true));
    }
}
