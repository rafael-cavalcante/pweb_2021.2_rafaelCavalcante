package br.com.rafaelcavalcante.biritashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelcavalcante.biritashop.model.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    
}
