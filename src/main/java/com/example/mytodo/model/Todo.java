package com.example.mytodo.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotBlank
    public String title;
    public Boolean isDone;
}
