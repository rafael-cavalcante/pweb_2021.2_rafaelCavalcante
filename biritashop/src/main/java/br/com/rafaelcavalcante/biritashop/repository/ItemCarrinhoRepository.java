package br.com.rafaelcavalcante.biritashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelcavalcante.biritashop.model.ItemCarrinho;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    
    List<ItemCarrinho> findByCarrinhoId(Long carrinhoId);
    
}
