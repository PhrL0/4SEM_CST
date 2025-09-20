package com.example.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository; // Importe o repositório
import com.example.demo.service.ProdutoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/novo")
    public ModelAndView showCreateForm() {
        return new ModelAndView("produtos/form", Map.of("produto", new Produto()));
    }

    @PostMapping("/novo")
    public String createProduct(@Valid Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return "produtos/form";
        }
        produtoService.saveProduto(produto);
        return "redirect:/produtos";
    }

    @GetMapping
    public ModelAndView getAll() {
        return new ModelAndView("produtos/list", Map.of("todos", produtoService.findAll()));
    }
    
    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            return new ModelAndView("produtos/form", Map.of("produto", produtoOptional.get()));
        }
        return new ModelAndView("redirect:/produtos");
    }

    @PostMapping("/edit/{id}")
    public String updateProduto(@PathVariable("id") Long id, @Valid Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return "produtos/form";
        }
        produto.setId(id);
        produtoService.editProduto(produto);
        return "redirect:/produtos";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduto(@PathVariable("id") Long id, @Valid Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return "produtos/form";
        }
        
        return "redirect:/produtos";
    }
}