package br.com.rafaelcavalcante.biritashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelcavalcante.biritashop.model.Dependente;

public interface DependenteRepository extends JpaRepository<Dependente, Long> {
    
}
