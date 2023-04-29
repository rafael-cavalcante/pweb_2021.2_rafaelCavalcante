package br.com.rafaelcavalcante.biritashop.controller;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.enums.Genero;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;
import br.com.rafaelcavalcante.biritashop.repository.RoleRepository;
import br.com.rafaelcavalcante.biritashop.services.CarrinhoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @GetMapping("/listar")
    public ModelAndView listarClientes() {
        List<Cliente> clientes = this.clienteRepository.findAll();
        return new ModelAndView("/cliente/listarClientes")
                .addObject("clientes", clientes);
    }

    @GetMapping("/adicionar")
    public ModelAndView formAdicionarCliente() {
        return new ModelAndView("/cliente/adicionarCliente")
                .addObject("generos", Genero.values())
                .addObject("roles", this.roleRepository.findAll())
                .addObject("cliente", new Cliente());
    }

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
                .orElseThrow(() -> new IllegalArgumentException("Cliente ID " + id + " Não Encontrado"));
        return new ModelAndView("/cliente/editarCliente")
                .addObject("generos", Genero.values())
                .addObject("cliente", cliente);
    }

    @PostMapping("/editar/{id}")
    @Transactional
    public String editarCliente(@PathVariable("id") Long id, Cliente cliente) {
        this.clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente ID " + id + " Não Encontrado"));
        this.clienteRepository.save(cliente);
        return "redirect:/cliente/listar";
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerCliente(@PathVariable("id") Long id) {
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente ID " + id + " Não Encontrado"));
        this.clienteRepository.delete(cliente);
        return new ModelAndView("redirect:/cliente/listar");
    }
}