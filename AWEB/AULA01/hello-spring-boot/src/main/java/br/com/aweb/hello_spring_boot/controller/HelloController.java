package br.com.aweb.hello_spring_boot.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
public class HelloController {
    @GetMapping()
    public String sayHello() {
        return "Olá mundo Spring Boot!";
    }

    @GetMapping("/ola")
    public String sayHelloCustom() {
        return "Olá mundo Spring Boot Custom!";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam("name") String userName) {
        return "Olá, " + userName + "! Bem-vindo(a)!!!";
    }

    @GetMapping("/calc")
    public double calculadora(@RequestParam double num1, @RequestParam double num2,@RequestParam(defaultValue = "soma") String op) {
        double operacao = 0;
        switch (op) {
            case "soma":
                operacao = num1 + num2;
                break;
            case "subtracao":
                operacao = num1 - num2;
            default:
                break;
        }

        return operacao;
    }

    @GetMapping("/idioma")
    public String idioma(@RequestParam(defaultValue = "Visitante") String user,@RequestParam(defaultValue = "pt") String language) {
        
        return language.equals("en") ? "Hello, " + user + "!Welcome." : "Olá, " + user + "!Bem-vindo(a).";
    }
    
    
    
    
    
}
