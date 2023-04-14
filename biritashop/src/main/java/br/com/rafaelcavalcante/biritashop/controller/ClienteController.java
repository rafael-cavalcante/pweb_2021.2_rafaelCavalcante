package br.com.rafaelcavalcante.biritashop.controller;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.enums.Genero;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private ClienteRepository clienteRepo;

    public ClienteController(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    @GetMapping("/listar")
    public ModelAndView listarClientes() {
        List<Cliente> clientes = this.clienteRepo.findAll();
        return new ModelAndView("/cliente/listarClientes")
                .addObject("clientes", clientes);
    }

    @GetMapping("/adicionar")
    public ModelAndView formAdicionarCliente() {
        return new ModelAndView("/cliente/adicionarCliente")
                .addObject("generos", Genero.values())
                .addObject(new Cliente());
    }

    @PostMapping("/adicionar")
    public String adicionarCliente(Cliente cliente) {
        this.clienteRepo.save(cliente);
        return "redirect:/cliente/listar";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView formEditarCliente(@PathVariable("id") Long id) {
        Cliente cliente = this.clienteRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente Não Encontrado " + id));
        return new ModelAndView("/cliente/editarCliente")
                .addObject("generos", Genero.values())
                .addObject(cliente);
    }

    @PostMapping("/editar/{id}")
    public String editarCliente(@PathVariable("id") Long id, Cliente cliente) {
        this.clienteRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente Não Encontrado " + id));
        this.clienteRepo.save(cliente);
        return "redirect:/cliente/listar";
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerCliente(@PathVariable("id") Long id) {
        Cliente cliente = this.clienteRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente Não Encontrado " + id));
        this.clienteRepo.delete(cliente);
        return new ModelAndView("redirect:/cliente/listar");
    }
}