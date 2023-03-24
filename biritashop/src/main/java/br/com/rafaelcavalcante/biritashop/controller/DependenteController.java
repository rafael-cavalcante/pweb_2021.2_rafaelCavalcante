package br.com.rafaelcavalcante.biritashop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @Autowired
    private DependenteRepository dependenteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /*@GetMapping("/listar")
    public ModelAndView listarDependentes() {
        List<Cliente> clientes = this.clienteRepository.findAll();
        ModelAndView mav = new ModelAndView("/dependente/listarDependentes");
        mav.addObject("clientes", clientes);
        return mav;
    }*/

    @GetMapping("/listar")
    public ModelAndView listarDependentes(@RequestParam(value = "clienteId", required = false) Long clienteId) {
        List<Cliente> clientes = this.clienteRepository.findAll();
        List<Dependente> dependentes = this.dependenteRepository.findByCliente_Id(clienteId);
        ModelAndView mav = new ModelAndView("/dependente/listarDependentes");
        mav.addObject("clientes", clientes);
        mav.addObject("clienteId", clienteId);
        mav.addObject("dependentes", dependentes);
        return mav;
    }

    /*@GetMapping("/listar/{clienteId}")
    public ModelAndView listarDependentesCliente(@PathVariable("clienteId") Long clienteId) {
        List<Dependente> dependentes = this.dependenteRepository.findByCliente_Id(clienteId);
        ModelAndView mav = new ModelAndView("/dependente/listarDependentes");
        mav.addObject("dependentes", dependentes);
        return mav;
    }*/

    @GetMapping("/adicionar")
    public ModelAndView formAdicionarDependente() {
        List<Cliente> clientes = this.clienteRepository.findAll();

        ModelAndView mav = new ModelAndView("/dependente/adicionarDependente");
        mav.addObject("clientes", clientes);
        mav.addObject(new Dependente());
        return mav;
    }

    @PostMapping("/adicionar")
    public String adicionarDependente(Dependente dependente) {
        this.dependenteRepository.save(dependente);
        return "redirect:/cliente/listar";
    }
}
