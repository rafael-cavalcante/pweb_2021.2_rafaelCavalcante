package br.com.rafaelcavalcante.biritashop.controller;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.dtos.ListaClienteDTO;
import br.com.rafaelcavalcante.biritashop.model.enums.Genero;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;
import br.com.rafaelcavalcante.biritashop.repository.RoleRepository;
import br.com.rafaelcavalcante.biritashop.services.CarrinhoService;
import br.com.rafaelcavalcante.biritashop.services.PDFService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.DocumentException;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private PDFService pdfService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public ModelAndView listarClientes() {
        List<Cliente> clientes = this.clienteRepository.findAll();

        return new ModelAndView("/cliente/listarClientes")
                .addObject("listaClienteDTO", new ListaClienteDTO())
                .addObject("clientes", clientes)
                .addObject("generos", Genero.values())
                .addObject("roles", this.roleRepository.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adicionar")
    public ModelAndView formAdicionarCliente() {
        return new ModelAndView("/cliente/adicionarCliente")
                .addObject("generos", Genero.values())
                .addObject("roles", this.roleRepository.findAll())
                .addObject("cliente", new Cliente());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/adicionar")
    @Transactional
    public String adicionarCliente(Cliente cliente) {
        cliente.setPassword(new BCryptPasswordEncoder().encode(cliente.getPassword()));
        cliente.setCarrinho(this.carrinhoService.criarCarrinho());
        this.clienteRepository.save(cliente);
        return "redirect:/cliente/listar";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView formEditarCliente(@PathVariable("id") Long id) {
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente ID " + id + " Nao Encontrado!"));

        return new ModelAndView("/cliente/formCliente")
                .addObject("cliente", cliente)
                .addObject("allGeneros", Genero.values())
                .addObject("allRoles", this.roleRepository.findAll());
    }

    @PostMapping("/editar/{id}")
    @Transactional
    public String editarCliente(@PathVariable("id") Long id, Cliente cliente) {
        if (this.clienteRepository.existsById(id)) {
            cliente.setPassword(new BCryptPasswordEncoder().encode(cliente.getPassword()));
            this.clienteRepository.save(cliente);
        }
        return "redirect:/cliente/listar";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/remover/{id}")
    public ModelAndView removerCliente(@PathVariable("id") Long id) {
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente ID " + id + " Não Encontrado"));

        this.clienteRepository.delete(cliente);
        return new ModelAndView("redirect:/cliente/listar");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pdf")
    public void gerarPdfDependentes(HttpServletResponse response)
            throws DocumentException, IOException {
        List<Cliente> clientes = this.clienteRepository.findAll();

        this.pdfService.gerarTemplateClientes(clientes, response);
    }
}