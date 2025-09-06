package com.example.to_do_list.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.example.to_do_list.model.Todo;
import com.example.to_do_list.repository.ToDoRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/todo")
public class todoController {
    @Autowired
    ToDoRepository todoRepository;
    // @GetMapping("/home")
    // public ModelAndView home(){

    //     //generico porque se ele instancia um ModelAndView a variavel é um modelandview
    //     var modelAndView = new ModelAndView("home");
    //     modelAndView.addObject("professor", "André Roberto da Silva");
    //     var alunos = List.of(
    //         "Isaac Newton",
    //         "Albert Einsten",
    //         "Marie Curie");
    //     modelAndView.addObject("alunos", alunos);
    //     modelAndView.addObject("ehVerdade", true);
    //     return modelAndView;

    // }

    @GetMapping
    public ModelAndView list(){
        // ModelAndView modelAndView = new ModelAndView("list");
        // modelAndView.addObject("todos",todoRepository.findAll());
        // return modelAndView;
        //return new ModelAndView("list",Map.of("todos", todoRepository.findAll()));
        return new ModelAndView("list",Map.of("todos", todoRepository.findAll(Sort.by("deadLine"))));
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("form",Map.of("todo", new Todo()));
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        var todo = todoRepository.findById(id);
        if(todo.isPresent()){
            return new ModelAndView("form",Map.of("todo", todo.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public String create(@Valid Todo todo,BindingResult result) {
        if(result.hasErrors()){
            return "form";
        }
        todoRepository.save(todo);
        return "redirect:/todo";
    }
    
    
}
