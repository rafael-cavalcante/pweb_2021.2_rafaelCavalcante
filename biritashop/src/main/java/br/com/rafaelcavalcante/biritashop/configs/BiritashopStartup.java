package br.com.rafaelcavalcante.biritashop.configs;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.Role;
import br.com.rafaelcavalcante.biritashop.model.enums.RoleName;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;
import br.com.rafaelcavalcante.biritashop.services.CarrinhoService;
import br.com.rafaelcavalcante.biritashop.services.RoleService;

@Component
public class BiritashopStartup implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private CarrinhoService carrinhoService;

    @Override
    public void run(String... args) throws Exception {
        gerarRoles();
        gerarAdmin();
    }

    @Transactional
    private void gerarRoles() {
        if (!this.roleService.findByNome(RoleName.ROLE_ADMIN).isPresent() ||
                !this.roleService.findByNome(RoleName.ROLE_USER).isPresent()) {
            this.roleService.adicionarRole(new Role(1L, RoleName.ROLE_ADMIN));
            this.roleService.adicionarRole(new Role(2L, RoleName.ROLE_USER));
        }
    }

    @Transactional
    private void gerarAdmin() {
        if (this.clienteRepository.findById(1L).isPresent()) {
            System.out.println("Usuário administrador já existe. Continuando...");
        } else {
            Cliente cliente = new Cliente();
            cliente.setId(1L);
            cliente.setUsername("biritashop");
            cliente.setPassword(new BCryptPasswordEncoder().encode("biritashop"));
            cliente.setRoles(this.roleService.listarRole(RoleName.ROLE_ADMIN));
            cliente.setCarrinho(this.carrinhoService.criarCarrinho());

            this.clienteRepository.save(cliente);   
        }
    }
}