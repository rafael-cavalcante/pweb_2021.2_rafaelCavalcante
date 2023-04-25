package br.com.rafaelcavalcante.biritashop.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.biritashop.model.Carrinho;
import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.ItemPedido;
import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.repository.CarrinhoRepository;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;
import br.com.rafaelcavalcante.biritashop.repository.ItemPedidoRepository;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    private CarrinhoRepository carrinhoRepo;

    private ProdutoRepository produtoRepo;

    private ClienteRepository clienteRepository;

    private ItemPedidoRepository itemPedidoRepository;

    public CarrinhoController(CarrinhoRepository carrinhoRepo, ProdutoRepository produtoRepo, ClienteRepository clienteRepository) {
        this.carrinhoRepo = carrinhoRepo;
        this.produtoRepo = produtoRepo;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/adicionar/{id}")
    public String adicionarCarrinho(Principal auth, @PathVariable("id") Long id) {
        Cliente cliente2 = this.clienteRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Cliente " + auth.getName() + "Não Encontrado"));
        Produto produto = this.produtoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto do ID " + id + " Não Encontrado"));
        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente2);
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setValorUnitario(produto.getPrecoVenda());

        List<ItemPedido> itensPedido = new ArrayList<ItemPedido>();
        itensPedido.add(itemPedido);

        //carrinho.setItensPedido(itensPedido);

        this.carrinhoRepo.save(carrinho);
        //itemPedido.setCarrinho(carrinho);

        this.itemPedidoRepository.save(itemPedido);

        return "redirect:/";
    }
}
