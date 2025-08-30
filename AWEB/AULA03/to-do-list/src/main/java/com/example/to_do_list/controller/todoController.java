package com.example.to_do_list.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/todo")
public class todoController {
    @GetMapping("/home")
    public ModelAndView home(){

        //generico porque se ele instancia um ModelAndView a variavel é um modelandview
        var modelAndView = new ModelAndView("home");
        modelAndView.addObject("professor", "André Roberto da Silva");
        var alunos = List.of(
            "Isaac Newton",
            "Albert Einsten",
            "Marie Curie");
        modelAndView.addObject("alunos", alunos);
        modelAndView.addObject("ehVerdade", true);
        return modelAndView;

    }
}
