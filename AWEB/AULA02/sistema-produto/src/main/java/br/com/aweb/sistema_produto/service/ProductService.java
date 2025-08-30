package br.com.aweb.sistema_produto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aweb.sistema_produto.model.Product;
import br.com.aweb.sistema_produto.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    //Listar todos os produtos
    public List<Product> allProducts(){
        return productRepository.findAll();
    }

    //Buscar o produto por id
    public Product findProduct(Long id){
        Optional<Product> pd = productRepository.findById(id);
        if(pd.isPresent()){
            return pd.get();
        }
        throw new RuntimeException("Produto não encontrado!!");  
    }

    //Cadastrar || alterar produto
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    //Remover produto
    public void deleteProduct(Long id){
        if(!productRepository.existsById(id)){
            throw new RuntimeException("Erro ao deletar produto!!");
        }
        productRepository.deleteById(id);
    }

    //Procurar por nome
    public List<Product> findNameProduct(String name){
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
