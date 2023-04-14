package br.com.rafaelcavalcante.biritashop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.Dependente;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;
import br.com.rafaelcavalcante.biritashop.repository.DependenteRepository;

@Controller
@RequestMapping("/dependente")
public class DependenteController {

    private DependenteRepository dependenteRepo;

    private ClienteRepository clienteRepo;

    public DependenteController(DependenteRepository dependenteRepo, ClienteRepository clienteRepo) {
        this.dependenteRepo = dependenteRepo;
        this.clienteRepo = clienteRepo;
    }

    @GetMapping("/listar")
    public ModelAndView listarDependentes(@RequestParam(value = "clienteId", required = false) Long clienteId) {
        List<Cliente> clientes = this.clienteRepo.findAll();
        List<Dependente> dependentes = this.dependenteRepo.findByCliente_Id(clienteId);
        return new ModelAndView("/dependente/listarDependentes")
                .addObject("clientes", clientes)
                .addObject("clienteId", clienteId)
                .addObject("dependentes", dependentes);
    }

    @GetMapping("/adicionar")
    public ModelAndView formAdicionarDependente() {
        List<Cliente> clientes = this.clienteRepo.findAll();
        return new ModelAndView("/dependente/adicionarDependente")
                .addObject("clientes", clientes)
                .addObject(new Dependente());
    }

    @PostMapping("/adicionar")
    public String adicionarDependente(Dependente dependente) {
        this.dependenteRepo.save(dependente);
        return "redirect:/dependente/listar/?clienteId=" + dependente.getCliente().getId();
    }

    @GetMapping("/editar/{id}")
    public ModelAndView formEditarDependente(@PathVariable("id") Long id) {
        Dependente dependente = this.dependenteRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
        List<Cliente> clientes = this.clienteRepo.findAll();
        return new ModelAndView("/dependente/editarDependente")
                .addObject("clientes", clientes)
                .addObject(dependente);
    }

    @PostMapping("/editar/{id}")
    public String editarDependente(@PathVariable("id") Long id, Dependente dependente) {
        this.dependenteRepo.save(dependente);
        return "redirect:/dependente/listar";
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerDependente(@PathVariable("id") Long id) {
        Dependente dependente = this.dependenteRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
        this.dependenteRepo.delete(dependente);
        return new ModelAndView("redirect:/dependente/listar");
    }
}
