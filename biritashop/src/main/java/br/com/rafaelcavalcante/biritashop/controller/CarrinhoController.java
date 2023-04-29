package br.com.rafaelcavalcante.biritashop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.ItemCarrinho;
import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.model.dto.PedidoDTO;
import br.com.rafaelcavalcante.biritashop.model.enums.FormaPagamento;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;
import br.com.rafaelcavalcante.biritashop.repository.ItemCarrinhoRepository;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @GetMapping("/listar")
    @Transactional
    public ModelAndView listarCarrinho() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Cliente cliente = this.clienteRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Cliente "  + "Não Encontrado"));
        List<ItemCarrinho> itensCarrinho = this.itemCarrinhoRepository.findByCarrinhoId(cliente.getCarrinho().getId());
                        return new ModelAndView("/carrinho/listarItensCarrinho")
                .addObject("itensCarrinho", itensCarrinho)
                .addObject("pedidoDTO", new PedidoDTO())
                .addObject("formasPagamento", FormaPagamento.values());
    }

    @GetMapping("/adicionar/{id}")
    public String adicionarCarrinho(@PathVariable("id") Long id) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Cliente cliente = this.clienteRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Cliente " + auth.getName() + "Não Encontrado"));
        Produto produto = this.produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto do ID " + id + " Não Encontrado"));

        ItemCarrinho itemCarrinho = new ItemCarrinho();
        itemCarrinho.setCarrinho(cliente.getCarrinho());
        itemCarrinho.setProduto(produto);

        this.itemCarrinhoRepository.save(itemCarrinho);

        return "redirect:/";
    }
}
