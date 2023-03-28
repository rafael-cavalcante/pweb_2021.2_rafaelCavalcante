package br.com.rafaelcavalcante.biritashop.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PedidoDTO {
    
    private List<ItemPedidoDTO> itens;
    private String formaPagamento;
}
