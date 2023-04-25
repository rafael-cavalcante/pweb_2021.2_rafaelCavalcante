package br.com.rafaelcavalcante.biritashop.configs;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.Role;
import br.com.rafaelcavalcante.biritashop.model.enums.RoleName;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;
import br.com.rafaelcavalcante.biritashop.repository.RoleRepository;

@Component
public class BiritashopStartup implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
        gerarRoles();
        gerarAdmin();
    }

    @Transactional
    private void gerarRoles() {
        if (!this.roleRepository.findByNome(RoleName.ROLE_ADMIN).isPresent() &&
                !this.roleRepository.findByNome(RoleName.ROLE_USER).isPresent()) {
            Role admin = new Role(1L, RoleName.ROLE_ADMIN);
            Role user = new Role(2L, RoleName.ROLE_USER);
            this.roleRepository.save(user);
            this.roleRepository.save(admin);
        }
    }

    @Transactional
    private void gerarAdmin() {
        if (this.clienteRepository.findById(1L).isPresent()) {
            System.out.println("Usuário administrador já existe. Continuando...");
        } else {
            List<Role> roles = this.roleRepository.getByNome(RoleName.ROLE_ADMIN);

            Cliente cliente = new Cliente(1L, "biritashop", new BCryptPasswordEncoder().encode("biritashop"), roles);

            this.clienteRepository.save(cliente);
        }
    }
}