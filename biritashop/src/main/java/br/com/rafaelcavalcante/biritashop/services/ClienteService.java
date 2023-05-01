package br.com.rafaelcavalcante.biritashop.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.dto.ClienteDTO;
import br.com.rafaelcavalcante.biritashop.model.enums.RoleName;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CarrinhoService carrinhoService;

    @Transactional
    public void adicionarCliente(ClienteDTO clienteDTO){
        Cliente cliente = new Cliente(clienteDTO);
        cliente.setRoles(this.roleService.listarRole(RoleName.ROLE_USER));
        cliente.setCarrinho(this.carrinhoService.criarCarrinho());

        this.clienteRepository.save(cliente);
    }
}
