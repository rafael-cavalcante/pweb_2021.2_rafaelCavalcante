package br.com.rafaelcavalcante.biritashop.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import br.com.rafaelcavalcante.biritashop.model.Pedido;
import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.model.dtos.PedidoDTO;
import br.com.rafaelcavalcante.biritashop.model.enums.FormaPagamento;
import br.com.rafaelcavalcante.biritashop.repository.PedidoRepository;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;
import br.com.rafaelcavalcante.biritashop.services.ItemCarrinhoService;
import br.com.rafaelcavalcante.biritashop.services.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ItemCarrinhoService itemCarrinhoService;

	@GetMapping("/listar")
	public ModelAndView listarPedidosId(@RequestParam(value = "clienteId", required = false) Long clienteId) {
		List<Cliente> clientes = this.clienteRepository.findAll();
		List<Pedido> pedidos = this.pedidoRepository.findByClienteId(clienteId);
		return new ModelAndView("/pedido/listarPedidos").addObject("clientes", clientes)
				.addObject("clienteId", clienteId).addObject("pedidos", pedidos);
	}

	@GetMapping("/adicionar")
	@Transactional
	public ModelAndView formAdicionarPedido() {
		List<Cliente> clientes = this.clienteRepository.findAll();
		List<Produto> produtos = this.produtoRepository.findAllByOrderByNomeAsc();
		return new ModelAndView("/pedido/adicionarPedido").addObject("clientes", clientes)
				.addObject("produtos", produtos).addObject("formasPagamento", FormaPagamento.values())
				.addObject("pedidoDTO", new PedidoDTO());
	}

	@PostMapping("/adicionar")
	@Transactional
	public ModelAndView adicionarPedido(PedidoDTO pedidoDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Cliente cliente = this.clienteRepository.findByUsername(auth.getName())
				.orElseThrow(() -> new IllegalArgumentException("Cliente " + auth.getName() + "Não Encontrado"));

		pedidoDTO.setCliente(cliente);
		List<ItemPedido> itensPedido = this.pedidoService.converterItensPedido(pedidoDTO.getItens());
		BigDecimal subTotal = this.pedidoService.calcularValorTotal(itensPedido);
		BigDecimal valorImposto = subTotal.multiply(new BigDecimal("0.1375"));
		BigDecimal valorTotal = subTotal.add(valorImposto);
		pedidoDTO.setValorPagamento(valorTotal.setScale(2, RoundingMode.HALF_UP));

		return new ModelAndView("/pedido/finalizarPedido").addObject("itensPedido", itensPedido).addObject("pedidoDTO",
				pedidoDTO);
	}

	@PostMapping("/finalizar")
	@Transactional
	public String finalizarPedido(PedidoDTO pedidoDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Cliente cliente = this.clienteRepository.findByUsername(auth.getName())
				.orElseThrow(() -> new IllegalArgumentException(
						"Cliente do ID [ " + pedidoDTO.getCliente().getId() + " ] Não Encontrado!"));
		
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setData(LocalDate.now());
		pedido.setFormaPagamento(pedidoDTO.getFormaPagamento());
		pedido.setValorPagamento(pedidoDTO.getValorPagamento());
		pedido.setNumeroCartao(pedidoDTO.getNumeroCartao());
		this.pedidoRepository.save(pedido);
		List<ItemPedido> itensPedido = this.pedidoService.converterItensPedido(pedido, pedidoDTO.getItens());
		this.itemPedidoRepository.saveAll(itensPedido);
		this.itemCarrinhoService.removerItensCarrinho(cliente.getCarrinho());
		return "redirect:/pedido/listar?clienteId=" + pedido.getCliente().getId();
	}

	@GetMapping("/editar/{id}")
	@Transactional
	public ModelAndView formEditarPedido(@PathVariable("id") Long id) {
		Pedido pedido = this.pedidoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Pedido do ID [" + id + "] Não Encontrado!"));
		List<ItemPedido> itensPedido = this.itemPedidoRepository.findByPedido_Id(id);
		return new ModelAndView("/pedido/editarPedido").addObject("itensPedido", itensPedido).addObject("pedidoDTO",
				pedidoService.converterPedidoDTO(pedido));
	}

	@PostMapping("/editar/{id}")
	@Transactional
	public String editarPedido(@PathVariable("id") Long id, PedidoDTO pedidoDTO) {
		Pedido pedido = this.pedidoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Pedido do ID [" + id + "] Não Encontrado!"));
		pedido.setNumeroCartao(pedido.getNumeroCartao());
		pedido.setValorPagamento(pedidoDTO.getValorPagamento());
		this.pedidoRepository.save(pedido);
		List<ItemPedido> itensPedido = this.pedidoService.editarItensPedido(pedido, pedidoDTO.getItens());
		this.itemPedidoRepository.saveAll(itensPedido);

		return "redirect:/pedido/listar?clienteId=" + pedido.getCliente().getId();
	}

	@GetMapping("/remover/{id}")
	public ModelAndView removerPedido(@PathVariable("id") Long id) {
		Pedido pedido = this.pedidoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
		this.pedidoRepository.delete(pedido);
		return new ModelAndView("redirect:/pedido/listar?clienteId=" + pedido.getCliente().getId());
	}

	@GetMapping("/item/listar/{id}")
	@Transactional
	public ModelAndView listarItens(@PathVariable("id") Long id) {
		Pedido pedido = this.pedidoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Pedido Não Encontrado " + id));
		BigDecimal subTotal = this.pedidoService.calcularValorTotal(pedido.getItens());
		BigDecimal valorImposto = subTotal.multiply(new BigDecimal("0.1375"));
		return new ModelAndView("/pedido/item/listarItensPedido").addObject("pedido", pedido)
				.addObject("subTotal", subTotal.setScale(2, RoundingMode.HALF_UP))
				.addObject("valorImposto", valorImposto.setScale(2, RoundingMode.HALF_UP))
				.addObject("valorTotal", pedido.getValorPagamento().setScale(2, RoundingMode.HALF_UP));
	}
}
