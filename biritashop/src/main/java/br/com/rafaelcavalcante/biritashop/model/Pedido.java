package br.com.rafaelcavalcante.biritashop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import br.com.rafaelcavalcante.biritashop.model.enums.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedido")
@Data
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    private FormaPagamento formaPagamento;
    private String numeroCartao;
    @Column(name = "valor_pagamento")
    private BigDecimal valorPagamento;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE)
    private List<ItemPedido> itens;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
