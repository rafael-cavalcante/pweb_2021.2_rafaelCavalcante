package br.com.rafaelcavalcante.biritashop.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.ItemPedido;
import br.com.rafaelcavalcante.biritashop.model.Pedido;
import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.model.dto.ItemPedidoDTO;
import br.com.rafaelcavalcante.biritashop.model.dto.PedidoDTO;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;
import br.com.rafaelcavalcante.biritashop.repository.ItemPedidoRepository;
import br.com.rafaelcavalcante.biritashop.repository.PedidoRepository;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;

@Controller
@RequestMapping("/itemPedido")
public class ItemPedidoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @GetMapping("/adicionar/{id}")
    public ModelAndView formAdicionarItemPedido(@PathVariable("id") Long id) {
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
        List<Produto> produtos = this.produtoRepository.findAll();
        ModelAndView mav = new ModelAndView("/itemPedido/adicionarItemPedido");
        mav.addObject("cliente", cliente);
        mav.addObject("produtos", produtos);
        mav.addObject("pedidoDTO", new PedidoDTO());
        return mav;
    }

    @Transactional
    @PostMapping("/compras/{id}")
    public ModelAndView compras(@PathVariable("id") Long id, PedidoDTO pedidoDTO) {
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setData(LocalDate.now());
        pedido.setFormaPagamento(pedidoDTO.getFormaPagamento());
        List<ItemPedido> itemPedidos = converter(pedido, pedidoDTO.getItens());
        this.pedidoRepository.save(pedido);
        this.itemPedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);
        return new ModelAndView("redirect:/");
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
}
