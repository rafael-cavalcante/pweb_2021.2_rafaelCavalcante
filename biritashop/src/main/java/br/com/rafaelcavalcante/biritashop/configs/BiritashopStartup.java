package br.com.rafaelcavalcante.biritashop.configs;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.rafaelcavalcante.biritashop.model.Role;
import br.com.rafaelcavalcante.biritashop.model.Usuario;
import br.com.rafaelcavalcante.biritashop.model.enums.RoleName;
import br.com.rafaelcavalcante.biritashop.repository.RoleRepository;
import br.com.rafaelcavalcante.biritashop.repository.UsuarioRepository;

@Component
public class BiritashopStartup implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public void run(String... args) throws Exception {
        gerarRoles();
        gerarAdmin();
    }

    @Transactional
    private void gerarRoles() {

        if (!this.roleRepo.findByNome(RoleName.ROLE_ADMIN).isPresent() &&
                !this.roleRepo.findByNome(RoleName.ROLE_USER).isPresent()) {
            Role admin = new Role(1L, RoleName.ROLE_ADMIN);
            Role user = new Role(2L, RoleName.ROLE_USER);
            this.roleRepo.save(user);
            this.roleRepo.save(admin);
        }
    }

    @Transactional
    private void gerarAdmin() {
        if (usuarioRepo.findById(1L).isPresent()) {
            System.out.println("Usuário administrador já existe. Continuando...");
        } else {
            Usuario Usuario = new Usuario(
                    1L,
                    "rafaelcavalcante",
                    new BCryptPasswordEncoder().encode("biritashop"),
                    roleRepo.getByNome(RoleName.ROLE_ADMIN));

            usuarioRepo.save(Usuario);
        }
    }
}
