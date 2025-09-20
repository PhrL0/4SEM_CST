package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto saveProduto(Produto p){
        return produtoRepository.save(p);
    }

    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    @Transactional
    public Produto editProduto(Produto produtoDoFormulario) {
        Long id = produtoDoFormulario.getId();
        
        Optional<Produto> produtoOptional = produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            Produto produtoDoBanco = produtoOptional.get();

            produtoDoBanco.setName(produtoDoFormulario.getName());
            produtoDoBanco.setDescription(produtoDoFormulario.getDescription());
            produtoDoBanco.setPrice(produtoDoFormulario.getPrice());
            produtoDoBanco.setStockQuantity(produtoDoFormulario.getStockQuantity());
            
            return produtoRepository.save(produtoDoBanco);
        } else {
            throw new EntityNotFoundException("Produto com ID " + id + " não encontrado.");
        }
    }

    @Transactional
    public void deletarProduto(Produto produtoDoFormulario) {
        Long id = produtoDoFormulario.getId();
        
        Optional<Produto> produtoOptional = produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            produtoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Produto com ID " + id + " não encontrado.");
        }
    }

}
