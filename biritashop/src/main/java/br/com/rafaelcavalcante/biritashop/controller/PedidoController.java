package br.com.rafaelcavalcante.biritashop.controller;

import java.util.List;

import br.com.rafaelcavalcante.biritashop.model.Pedido;
import br.com.rafaelcavalcante.biritashop.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/listar")
    public ModelAndView listarPedidos(@RequestParam(value = "clienteId", required = false) Long clienteId){
        List<Cliente> clientes = this.clienteRepository.findAll();
        List<Pedido> pedidos = this.pedidoRepository.findByCliente_Id(clienteId);
        ModelAndView mav = new ModelAndView("/pedido/listarPedidos");
        mav.addObject("clientes", clientes);
        mav.addObject("clienteId", clienteId);
        mav.addObject("pedidos", pedidos);
        return mav;
    }

    @GetMapping("/adicionar")
    public ModelAndView formAdicionar() {
        List<Cliente> clientes = this.clienteRepository.findAll();
        ModelAndView mav = new ModelAndView("/pedido/adicionarPedido");
        mav.addObject("clientes", clientes);
        mav.addObject(new Cliente());
        return mav;
    }

    @PostMapping("/adicionar")
    public String adicionar(Cliente cliente) {
        return "redirect:/itemPedido/adicionar/" + cliente.getId();
    }
}
