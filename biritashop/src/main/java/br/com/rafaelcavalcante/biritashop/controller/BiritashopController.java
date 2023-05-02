package br.com.rafaelcavalcante.biritashop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.model.dtos.ClienteDTO;
import br.com.rafaelcavalcante.biritashop.model.mappes.ClienteMapper;
import br.com.rafaelcavalcante.biritashop.services.ClienteService;
import br.com.rafaelcavalcante.biritashop.services.ProdutoService;

@Controller
@RequestMapping("/")
public class BiritashopController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ModelAndView index(@RequestParam(value = "produtoInfo", required = false) String produtoInfo,
            @PageableDefault(sort = "nome", direction = Sort.Direction.ASC, value = 12) Pageable pageable) {
        Page<Produto> produtos;

        if (produtoInfo == null) {
            produtos = this.produtoService.listarPaginaProdutos(pageable);
        } else {
            produtos = this.produtoService.listarPaginaProdutosNome(produtoInfo, pageable);
        }
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setUsername("Rafael");
        clienteDTO.setPassword("SenhaRafael");

        System.out.println(ClienteMapper.INSTANCE.toCliente(clienteDTO));

        return new ModelAndView("/index")
                .addObject("produtos", produtos);
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
        return "redirect:/";
    }

    @GetMapping("/cadastrar")
    public ModelAndView formCadastrar() {
        return new ModelAndView("/cadastrar");
    }

    @PostMapping("/cadastrar")
    public String cadastrar(ClienteDTO clienteDTO) {
        if (this.clienteService.verificarCliente(clienteDTO.getUsername())) {
            return "redirect:/cadastrar?error";
        }
        this.clienteService.adicionarCliente(clienteDTO);
        return "redirect:/login";
    }
}