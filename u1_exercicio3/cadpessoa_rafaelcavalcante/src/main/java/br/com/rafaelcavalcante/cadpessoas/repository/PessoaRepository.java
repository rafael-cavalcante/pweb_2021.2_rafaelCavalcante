package br.com.rafaelcavalcante.cadpessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelcavalcante.cadpessoas.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
