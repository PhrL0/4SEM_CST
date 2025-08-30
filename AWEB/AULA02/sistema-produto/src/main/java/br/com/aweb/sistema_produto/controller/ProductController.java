package br.com.aweb.sistema_produto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.aweb.sistema_produto.model.Product;
import br.com.aweb.sistema_produto.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    ProductService productService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.allProducts());
        return "list";
    }

    @GetMapping("/new")
    public String create(Model model){
        model.addAttribute("product", new Product());
        return "form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model,RedirectAttributes attributes){
        try {
            model.addAttribute("product", productService.findProduct(id));
            return "form";
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/products";
        }
    }
    @PostMapping
    public String save(@Valid Product product,
            BindingResult result,
            RedirectAttributes attributes){
        if(result.hasErrors()){
            return "form";
        }
        productService.createProduct(product);
        attributes.addFlashAttribute("message","Produto salvo com sucesso!");
        return "redirect:/products";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        try {
            productService.deleteProduct(id);
            attributes.addFlashAttribute("message","Produto excluido com sucesso!");
            return "form";
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/search")
    public String findName(@RequestParam(required = false)String name, Model model,RedirectAttributes attributes) {
        if(name != null && !name.isBlank()){
            List<Product> products = productService.findNameProduct(name);
            model.addAttribute("products", products);
        }
        return "search";
    }
    




    
}
