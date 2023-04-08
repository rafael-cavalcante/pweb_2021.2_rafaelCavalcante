package br.com.rafaelcavalcante.biritashop.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rafaelcavalcante.biritashop.model.ItemPedido;
import br.com.rafaelcavalcante.biritashop.model.Pedido;
import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.model.dto.ItemPedidoDTO;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;

@Service
public class PedidoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ItemPedido> converterItensPedido(Pedido pedido, List<ItemPedidoDTO> itensPedidoDTO) {
        List<ItemPedidoDTO> filtrado = itensPedidoDTO.stream()
                .filter(Objects::nonNull)
                .filter(item -> Objects.nonNull(item.getId()))
                .peek(item -> item.setQuantidade(item.getQuantidade() != null ? item.getQuantidade() : 1L))
                .collect(Collectors.toList());
        return filtrado.stream().map(itemPedidoDTO -> {
            Produto produto = this.produtoRepository.findById(itemPedidoDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto Não Encontrado " + itemPedidoDTO.getId()));
            ItemPedido itemPedido = ItemPedido.builder()
                    .quantidade(itemPedidoDTO.getQuantidade())
                    .valorUnitario(produto.getPrecoVenda())
                    .produto(produto)
                    .pedido(pedido)
                    .build();
            return itemPedido;
        }).collect(Collectors.toList());
    }

    public BigDecimal calcularValorTotal(ItemPedido itemPedido) {
        return itemPedido.getValorUnitario()
                .multiply(BigDecimal.valueOf(itemPedido.getQuantidade()));
    }

    public BigDecimal calcularValorTotal(List<ItemPedido> itensPedido) {
        return itensPedido.stream()
                .map(item -> calcularValorTotal(item))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
