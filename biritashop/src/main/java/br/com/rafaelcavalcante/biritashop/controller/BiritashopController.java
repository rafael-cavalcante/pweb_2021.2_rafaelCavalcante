package br.com.rafaelcavalcante.biritashop.controller;

import java.util.ArrayList;
import java.util.List;

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

import br.com.rafaelcavalcante.biritashop.model.BreadcrumbItem;
import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.model.dtos.ClienteDTO;
import br.com.rafaelcavalcante.biritashop.model.enums.Genero;
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
            @PageableDefault(sort = "nome", direction = Sort.Direction.ASC, size = 12, page = 0) Pageable pageable) {
        Page<Produto> produtos;

        if (produtoInfo == null) {
            produtos = this.produtoService.listarPaginaProdutos(pageable);
        } else {
            produtos = this.produtoService.listarPaginaProdutosNome(produtoInfo, pageable);

        }
        /*
         * ClienteDTO clienteDTO = new ClienteDTO();
         * clienteDTO.setUsername("Rafael");
         * clienteDTO.setPassword("SenhaRafael");
         * 
         * System.out.println(ClienteMapper.INSTANCE.toCliente(clienteDTO));
         */

        List<BreadcrumbItem> currentBreadCrumb = new ArrayList<>();
        currentBreadCrumb.add(new BreadcrumbItem("/", "Home", true));

        return new ModelAndView("/home")
                .addObject("currentBreadCrumb", currentBreadCrumb)
                .addObject("produtos", produtos);
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    @GetMapping("/cadastrar")
    public ModelAndView formCadastrar() {
        return new ModelAndView("/cadastrar")
                .addObject("allGeneros", Genero.values());
    }

    @PostMapping("/cadastrar")
    public String cadastrar(ClienteDTO clienteDTO) {
        if (this.clienteService.verificarCliente(clienteDTO.getUsername())) {
            return "redirect:/cadastrar?error";
        }
        this.clienteService.adicionarCliente(clienteDTO);
        return "redirect:/login";
    }

    @GetMapping("/teste")
    public ModelAndView teste(){
        return new ModelAndView("/teste");
    }

    @GetMapping("/prototipo")
    public ModelAndView prototipo(){
        return new ModelAndView("/prototipo");
    }
}