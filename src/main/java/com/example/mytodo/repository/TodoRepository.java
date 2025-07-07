package com.example.mytodo.repository;

import com.example.mytodo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long>{

 }
