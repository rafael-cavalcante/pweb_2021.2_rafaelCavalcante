package br.com.rafaelcavalcante.biritashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelcavalcante.biritashop.model.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByCliente_Id(Long clienteId);
}
