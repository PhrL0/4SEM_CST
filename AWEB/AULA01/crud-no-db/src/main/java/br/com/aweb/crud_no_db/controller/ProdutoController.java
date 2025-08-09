package br.com.aweb.crud_no_db.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.aweb.crud_no_db.dto.ProdutoDTO;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    private Map<Long,ProdutoDTO> produtosMap = new HashMap<>();
    private Long nextId = 1L;


    //Listar todos produtos
    @GetMapping
    public List<ProdutoDTO> allProducts(){
        return new ArrayList<>(produtosMap.values());
    }

    //Buscar produto por id
    @GetMapping("/{id}")
    public ProdutoDTO getProductById(@PathVariable Long id){
        return produtosMap.get(id);
    }

    @PostMapping
    public ProdutoDTO createProduct(@RequestBody ProdutoDTO produto){
        produto.setId(nextId++);
        produtosMap.put(produto.getId(), produto);

        return produto;
    }

    @DeleteMapping("/{id}")
        public String deleteProduct(@PathVariable Long id){
            if(produtosMap.containsKey(id)){
                produtosMap.remove(id);
            } else {
                return "Produto não encontrado";
            }

            return "Produt removido com sucesso!";
        }
    }


