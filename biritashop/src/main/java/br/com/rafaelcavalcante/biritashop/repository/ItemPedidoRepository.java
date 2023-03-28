package br.com.rafaelcavalcante.biritashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelcavalcante.biritashop.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    
}
