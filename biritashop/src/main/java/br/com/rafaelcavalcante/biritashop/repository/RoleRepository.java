package br.com.rafaelcavalcante.biritashop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelcavalcante.biritashop.model.Role;
import br.com.rafaelcavalcante.biritashop.model.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> getByNome(RoleName rolenome);

    Optional<Role> findByNome(RoleName roleName);
}
