package br.com.rafaelcavalcante.biritashop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @GetMapping("/adicionar")
    public ModelAndView formAdicionarPedido() {
        List<Cliente> clientes = this.clienteRepository.findAll();
        ModelAndView mav = new ModelAndView("/pedido/adicionarPedido");
        mav.addObject("clientes", clientes);
        mav.addObject(new Cliente());
        return mav;
    }

    @PostMapping("/adicionar")
    public String adicionarPedido(Cliente cliente) {
        return "redirect:/itemPedido/adicionar/" + cliente.getId();
    }
}
