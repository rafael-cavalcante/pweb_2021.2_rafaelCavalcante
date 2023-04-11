package br.com.rafaelcavalcante.biritashop.controller;

import java.time.LocalDate;
import java.util.List;

import br.com.rafaelcavalcante.biritashop.model.Pedido;
import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.model.dto.PedidoDTO;
import br.com.rafaelcavalcante.biritashop.model.enums.FormaPagamento;
import br.com.rafaelcavalcante.biritashop.repository.PedidoRepository;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;
import br.com.rafaelcavalcante.biritashop.services.PedidoService;

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

    private ClienteRepository clienteRepo;

    private PedidoRepository pedidoRepo;

    private ProdutoRepository produtoRepo;

    private ItemPedidoRepository itemPedidoRepo;

    private PedidoService pedidoService;

    public PedidoController(
            ClienteRepository clienteRepo, PedidoRepository pedidoRepo,
            ProdutoRepository produtoRepo, ItemPedidoRepository itemPedidoRepo,
            PedidoService pedidoService) {
        this.clienteRepo = clienteRepo;
        this.pedidoRepo = pedidoRepo;
        this.produtoRepo = produtoRepo;
        this.itemPedidoRepo = itemPedidoRepo;
        this.pedidoService = pedidoService;
    }

    /*
     * @GetMapping("/listar")
     * public ModelAndView listarPedidos(@RequestParam(value = "clienteId", required
     * = false) Long clienteId) {
     * List<Cliente> clientes = this.clienteRepo.findAll();
     * List<Pedido> pedidos = this.pedidoRepo.findByClienteId(clienteId);
     * ModelAndView mav = new ModelAndView("/pedido/listarPedidos");
     * mav.addObject("clientes", clientes);
     * mav.addObject("clienteId", clienteId);
     * mav.addObject("pedidos", pedidos);
     * return mav;
     * }
     */

    @GetMapping("/listar")
    public ModelAndView listarPedidosId(@RequestParam(value = "clienteId", required = false) Long clienteId) {
        List<Cliente> clientes = this.clienteRepo.findAll();
        List<Pedido> pedidos = this.pedidoRepo.findByClienteId(clienteId);
        return new ModelAndView("/pedido/listarPedidos")
                .addObject("clientes", clientes)
                .addObject("clienteId", clienteId)
                .addObject("pedidos", pedidos);
    }

    @GetMapping("/adicionar")
    public ModelAndView formAdicionarPedido() {
        List<Cliente> clientes = this.clienteRepo.findAll();
        List<Produto> produtos = this.produtoRepo.findAllByOrderByNomeAsc();
        ModelAndView mav = new ModelAndView("/pedido/adicionarPedido");
        mav.addObject("clientes", clientes);
        mav.addObject("produtos", produtos);
        mav.addObject("formasPagamento", FormaPagamento.values());
        mav.addObject(new PedidoDTO());
        return mav;
    }

    @Transactional
    @PostMapping("/adicionar")
    public ModelAndView adicionarPedido(PedidoDTO pedidoDTO) {
        Cliente cliente = this.clienteRepo.findById(pedidoDTO.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + pedidoDTO.getCliente().getId()));
        ModelAndView mav = new ModelAndView("/pedido/finalizarPedido");
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setData(LocalDate.now());
        pedido.setFormaPagamento(pedidoDTO.getFormaPagamento());
        List<ItemPedido> itensPedido = this.pedidoService.converterItensPedido(pedido, pedidoDTO.getItens());
        this.pedidoRepo.save(pedido);
        this.itemPedidoRepo.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        mav.addObject("pedido", pedido);
        return mav;

    }

    @PostMapping("/finalizar/{id}")
    public String finalizarPedido(@PathVariable("id") Long pedidoId, Pedido pedido) {
        Pedido pedidoFinalizado = this.pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido Id " + pedidoId + " Não Encontrado!"));
        pedidoFinalizado.setValorPagamento(pedido.getValorPagamento());
        pedidoFinalizado.setNumeroCartao(pedido.getNumeroCartao());
        this.pedidoRepo.save(pedidoFinalizado);
        return "redirect:/pedido/listar?clienteId=" + pedidoFinalizado.getCliente().getId();
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerPedido(@PathVariable("id") Long id) {
        Pedido pedido = this.pedidoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
        this.pedidoRepo.delete(pedido);
        return new ModelAndView("redirect:/pedido/listar?clienteId=" + pedido.getCliente().getId());
    }

    @GetMapping("/item/listar/{id}")
    public ModelAndView listarItens(@PathVariable("id") Long id) {
        Pedido pedido = this.pedidoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido Não Encontrado " + id));
        ModelAndView mav = new ModelAndView("/pedido/item/listarItens");
        mav.addObject("pedido", pedido);
        mav.addObject("valorTotal", this.pedidoService.calcularValorTotal(pedido.getItens()));
        return mav;
    }
}
