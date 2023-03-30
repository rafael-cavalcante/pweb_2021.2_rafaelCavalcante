package br.com.rafaelcavalcante.biritashop.model.dto;

import java.util.List;

import br.com.rafaelcavalcante.biritashop.model.enums.FormaPagamento;
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
    private FormaPagamento formaPagamento;
}
