package br.com.rafaelcavalcante.biritashop.repository;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
