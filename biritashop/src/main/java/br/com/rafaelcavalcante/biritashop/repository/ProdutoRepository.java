package br.com.rafaelcavalcante.biritashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelcavalcante.biritashop.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
