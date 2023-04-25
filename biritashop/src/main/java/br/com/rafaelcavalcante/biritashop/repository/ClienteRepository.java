package br.com.rafaelcavalcante.biritashop.repository;

import br.com.rafaelcavalcante.biritashop.model.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByUsername(String username);

}
