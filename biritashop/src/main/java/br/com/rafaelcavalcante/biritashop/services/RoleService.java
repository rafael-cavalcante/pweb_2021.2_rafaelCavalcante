package br.com.rafaelcavalcante.biritashop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rafaelcavalcante.biritashop.model.Role;
import br.com.rafaelcavalcante.biritashop.model.enums.RoleName;
import br.com.rafaelcavalcante.biritashop.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> listarRole(RoleName roleName) {
        return this.roleRepository.getByNome(roleName);
    }

    public Optional<Role> findByNome(RoleName roleName){
        return this.roleRepository.findByNome(roleName);
    }

    public void adicionarRole(Role role){
        this.roleRepository.save(role);
    }
}
