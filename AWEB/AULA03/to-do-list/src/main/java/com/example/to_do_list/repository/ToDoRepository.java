package com.example.to_do_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.to_do_list.model.Todo;

public interface ToDoRepository extends JpaRepository<Todo,Long> {
    
}
