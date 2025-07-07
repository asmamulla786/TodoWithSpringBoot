package com.example.mytodo.service;

import com.example.mytodo.model.Todo;
import com.example.mytodo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;

@ExtendWith(SpringExtension.class)
class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

    @Test
    void getTodos() {
        Todo todo1 = new Todo();
        todo1.id = 1L;
        todo1.title = "todo1";
        todo1.isDone = false;

        Todo todo2 = new Todo();
        todo2.id = 2L;
        todo2.title = "todo2";
        todo2.isDone = true;

        List<Todo> todos = List.of(todo1, todo2);
        when(todoRepository.findAll()).thenReturn(todos);

        List<Todo> result = todoService.getTodos();
        assertEquals(2, result.size());
        assertEquals("todo1", result.get(0).title);
        assertEquals("todo2", result.get(1).title);
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void addTodo() {
        Todo input = new Todo();
        input.isDone = false;
        input.title = "todo1";

        Todo addedTodo = new Todo();
        addedTodo.id = 1L;
        addedTodo.isDone = false;
        addedTodo.title = "todo1";

        when(todoRepository.save(input)).thenReturn(addedTodo);


        Todo result = todoService.addTodo(input);
        assertNotNull(result);
        assertEquals(1L, result.id);
        assertEquals("todo1", result.title);
        assertEquals(false, result.isDone);

        verify(todoRepository, times(1)).save(input);

    }

    @Test
    void deleteTodo() {
        Long id = 1L;
        Todo todo = new Todo();
        todo.id = id;
        todo.title = "todo1";
        todo.isDone = false;

        when(todoRepository.findById(id)).thenReturn(java.util.Optional.of(todo));
        doNothing().when(todoRepository).deleteById(id);

        Todo result = todoService.deleteTodo(id);
        assertNotNull(result);
        assertEquals(id, result.id);
        assertEquals("todo1", result.title);
        assertFalse(result.isDone);
        verify(todoRepository, times(1)).findById(id);
        verify(todoRepository, times(1)).deleteById(id);
    }

    @Test
    void updateTodo() {
        Long id = 1L;
        Todo existing = new Todo();
        existing.id = id;
        existing.title = "old title";
        existing.isDone = false;

        Todo updated = new Todo();
        updated.id = id;
        updated.title = "new title";
        updated.isDone = true;

        when(todoRepository.findById(id)).thenReturn(java.util.Optional.of(existing));
        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Todo result = todoService.updateTodo(id, updated);
        assertNotNull(result);
        assertEquals(id, result.id);
        assertEquals("new title", result.title);
        assertTrue(result.isDone);
        verify(todoRepository, times(1)).findById(id);
        verify(todoRepository, times(1)).save(existing);
    }
}