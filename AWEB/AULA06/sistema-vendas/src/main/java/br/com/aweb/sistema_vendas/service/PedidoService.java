package br.com.aweb.sistema_vendas.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import br.com.aweb.sistema_vendas.model.Cliente;
import br.com.aweb.sistema_vendas.model.ItemPedido;
import br.com.aweb.sistema_vendas.model.Pedido;
import br.com.aweb.sistema_vendas.model.Produto;
import br.com.aweb.sistema_vendas.model.StatusPedido;
import br.com.aweb.sistema_vendas.repository.PedidoRepository;
import br.com.aweb.sistema_vendas.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Pedido criarPedido(Cliente cliente){
        return pedidoRepository.save(new Pedido(cliente));
    }

    @Transactional
    public void adicionarItem(Long pedidoId,Long produtoId, Integer quantidade){
        var isExistPedido = pedidoRepository.findById(pedidoId);
        var isExistProduto = produtoRepository.findById(produtoId);
        boolean isEnough = quantidade > isExistProduto.get().getQuantidadeEmEstoque();
        if(!isExistPedido.isPresent() || isExistProduto.isPresent()){
            throw new IllegalArgumentException("Nada encontrado");
        }
        Pedido pedido = isExistPedido.get();
        Produto produto = isExistProduto.get();
        if(!isEnough && pedido.getStatus() == StatusPedido.ATIVO){
            ItemPedido item = new ItemPedido(produto,quantidade);
            item.setPedido(pedido);
            
            pedido.getItens().add(item);

            produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - quantidade);

            calcularValorTotal(pedido);(produto);
            pedidoRepository.save(pedido);
            produtoRepository.save(produto);
        }
    }

    public void removerItem(Long pedidoId,Long itemId){
        var isExistPedido = pedidoRepository.findById(pedidoId);
        if(!isExistPedido.isPresent()){
            throw new IllegalArgumentException("Pedido não encontrado");
        }
        
    }

    private void calcularValorTotal(Pedido pedido){
        BigDecimal total = BigDecimal.ZERO;

        for(ItemPedido item : pedido.getItens()){
            BigDecimal valorItem = item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()));
            total = total.add(valorItem);
        }

        pedido.setValor_total(total);
    }
}
