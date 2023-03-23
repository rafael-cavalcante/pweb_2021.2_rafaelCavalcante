package br.com.rafaelcavalcante.biritashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelcavalcante.biritashop.model.Dependente;

public interface DependenteRepository extends JpaRepository<Dependente, Long> {

    List<Dependente> findByCliente_Id(Long clienteId);
    
}
