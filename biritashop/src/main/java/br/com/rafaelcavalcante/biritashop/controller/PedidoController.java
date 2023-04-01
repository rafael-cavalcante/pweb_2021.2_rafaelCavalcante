package br.com.rafaelcavalcante.biritashop.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.com.rafaelcavalcante.biritashop.model.Pedido;
import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.model.dto.ItemPedidoDTO;
import br.com.rafaelcavalcante.biritashop.model.dto.PedidoDTO;
import br.com.rafaelcavalcante.biritashop.model.enums.FormaPagamento;
import br.com.rafaelcavalcante.biritashop.repository.PedidoRepository;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.ItemPedido;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;
import br.com.rafaelcavalcante.biritashop.repository.ItemPedidoRepository;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @GetMapping("/listar")
    public ModelAndView listarPedidos(@RequestParam(value = "clienteId", required = false) Long clienteId) {
        List<Cliente> clientes = this.clienteRepository.findAll();
        List<Pedido> pedidos = this.pedidoRepository.findByClienteId(clienteId);
        ModelAndView mav = new ModelAndView("/pedido/listarPedidos");
        mav.addObject("clientes", clientes);
        mav.addObject("clienteId", clienteId);
        mav.addObject("pedidos", pedidos);
        return mav;
    }

    @GetMapping("/adicionar")
    public ModelAndView formAdicionarPedido() {
        List<Cliente> clientes = this.clienteRepository.findAll();
        List<Produto> produtos = this.produtoRepository.findAllByOrderByNomeAsc();
        ModelAndView mav = new ModelAndView("/pedido/adicionarPedido");
        mav.addObject("clientes", clientes);
        mav.addObject("produtos", produtos);
        mav.addObject(new PedidoDTO());
        mav.addObject("tipos", FormaPagamento.values());
        return mav;
    }

    @Transactional
    @PostMapping("/adicionar")
    public ModelAndView adicionarPedido(PedidoDTO pedidoDTO) {
        Cliente cliente = this.clienteRepository.findById(pedidoDTO.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + pedidoDTO.getCliente().getId()));
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setData(LocalDate.now());
        pedido.setFormaPagamento(pedidoDTO.getFormaPagamento());
        List<ItemPedido> itensPedido = validarQuantidade(converter(pedido, pedidoDTO.getItens()));
        this.pedidoRepository.save(pedido);
        this.itemPedidoRepository.saveAll(itensPedido);
        return new ModelAndView("redirect:/pedido/listar?clienteId=" + cliente.getId());
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerPedido(@PathVariable("id") Long id) {
        Pedido pedido = this.pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
        this.pedidoRepository.delete(pedido);
        return new ModelAndView("redirect:/pedido/listar?clienteId=" + pedido.getCliente().getId());
    }

    public List<ItemPedido> converter(Pedido pedido, List<ItemPedidoDTO> itemPedidos) {
        List<ItemPedidoDTO> filtrado = itemPedidos.stream().filter(c -> c.getId() != null).collect(Collectors.toList());
        return filtrado.stream().map(dto -> {
            Produto temp = this.produtoRepository.findById(dto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + dto.getId()));
            ItemPedido itemPedido = ItemPedido.builder()
                    .quantidade(dto.getQuantidade())
                    .valorUnitario(temp.getPrecoVenda())
                    .produto(temp)
                    .pedido(pedido)
                    .build();
            return itemPedido;
        }).collect(Collectors.toList());
    }

    public List<ItemPedido> validarQuantidade(List<ItemPedido> itensPedidos) {
        for (ItemPedido item : itensPedidos) {
            if (item.getQuantidade() == null) {
                item.setQuantidade(1L);
            }
        }
        return itensPedidos;
    }
}
