package br.com.rafaelcavalcante.biritashop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;
import br.com.rafaelcavalcante.biritashop.services.ProdutoService;

@Controller
@RequestMapping("/")
public class BiritashopController {

    private ClienteRepository clienteRepository;

    private ProdutoService produtoService;

    public BiritashopController(ClienteRepository clienteRepository, ProdutoService produtoService) {
        this.clienteRepository = clienteRepository;
        this.produtoService = produtoService;
    }

    @GetMapping
    public ModelAndView index(@PageableDefault(sort = "nome", direction = Sort.Direction.ASC, value = 12) Pageable pageable) {
        Page<Produto> produtos = this.produtoService.listarPaginaProdutos(pageable);
        List<Cliente> clientes = this.clienteRepository.findAll();

        return new ModelAndView("/index")
                .addObject("clientes", clientes)
                .addObject("produtos", produtos);       
    }

    @GetMapping("/public/sobre")
    public ModelAndView sobre() {
        return new ModelAndView("/public/sobre");
    }

    @GetMapping("/public/contato")
    public ModelAndView contato() {
        return new ModelAndView("/public/contato");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }
}