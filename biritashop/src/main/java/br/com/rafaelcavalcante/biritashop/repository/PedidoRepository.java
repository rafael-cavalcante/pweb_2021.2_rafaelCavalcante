package br.com.rafaelcavalcante.biritashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelcavalcante.biritashop.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}
