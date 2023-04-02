package br.com.rafaelcavalcante.biritashop.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemPedidoDTO {
    
    private Long id;
    private String nome;
    private BigDecimal valorUnitario;
    private Long quantidade;

}
