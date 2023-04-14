package br.com.rafaelcavalcante.biritashop.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.rafaelcavalcante.biritashop.model.ItemPedido;
import br.com.rafaelcavalcante.biritashop.model.Pedido;
import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.model.dto.ItemPedidoDTO;
import br.com.rafaelcavalcante.biritashop.repository.ItemPedidoRepository;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;

@Service
public class PedidoService {

    private ProdutoRepository produtoRepo;

    private ItemPedidoRepository itemPedidoRepo;

    public PedidoService(ProdutoRepository produtoRepo,
            ItemPedidoRepository itemPedidoRepo) {
        this.produtoRepo = produtoRepo;
        this.itemPedidoRepo = itemPedidoRepo;
    }

    public List<ItemPedido> editarItensPedido(Pedido pedido, List<ItemPedido> itensPedido) {
        List<ItemPedido> filtrado = itensPedido.stream()
                .filter(Objects::nonNull)
                .filter(item -> Objects.nonNull(item.getId()))
                .peek(item -> item.setQuantidade(
                        item.getQuantidade() != null ? item.getQuantidade() : 1L))
                .collect(Collectors.toList());

        return filtrado.stream().map(itemPedidoEditado -> {
            ItemPedido itemPedido = this.itemPedidoRepo.findById(itemPedidoEditado.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "ItemPedido Não Encontrado " + itemPedidoEditado.getId()));

            Produto produto = this.produtoRepo.findById(itemPedido.getProduto().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Produto Não Encontrado " + itemPedido.getProduto().getId()));
            itemPedido.setQuantidade(itemPedidoEditado.getQuantidade());
            itemPedido.setProduto(produto);
            itemPedido.setPedido(pedido);
            return itemPedido;
        }).collect(Collectors.toList());
    }

    public List<ItemPedido> converterItensPedido(Pedido pedido, List<ItemPedidoDTO> itensPedido) {
        List<ItemPedidoDTO> filtrado = itensPedido.stream()
                .filter(Objects::nonNull)
                .filter(item -> Objects.nonNull(item.getId()))
                .peek(item -> item.setQuantidade(
                        item.getQuantidade() != null ? item.getQuantidade() : 1L))
                .collect(Collectors.toList());
        return filtrado.stream().map(itemPedidoDTO -> {
            Produto produto = this.produtoRepo.findById(itemPedidoDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Produto Não Encontrado " + itemPedidoDTO.getId()));
            ItemPedido itemPedido = ItemPedido.builder()
                    .quantidade(itemPedidoDTO.getQuantidade())
                    .valorUnitario(produto.getPrecoVenda())
                    .produto(produto)
                    .pedido(pedido)
                    .build();
            return itemPedido;
        }).collect(Collectors.toList());
    }

    public List<ItemPedido> converterItensPedido(List<ItemPedidoDTO> itensPedido) {
        List<ItemPedidoDTO> filtrado = itensPedido.stream()
                .filter(Objects::nonNull)
                .filter(item -> Objects.nonNull(item.getId()))
                .peek(item -> item.setQuantidade(
                        item.getQuantidade() != null ? item.getQuantidade() : 1L))
                .collect(Collectors.toList());
        return filtrado.stream().map(itemPedidoDTO -> {
            Produto produto = this.produtoRepo.findById(itemPedidoDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Produto Não Encontrado " + itemPedidoDTO.getId()));
            ItemPedido itemPedido = ItemPedido.builder()
                    .quantidade(itemPedidoDTO.getQuantidade())
                    .valorUnitario(produto.getPrecoVenda())
                    .produto(produto)
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
