package br.com.rafaelcavalcante.biritashop.model.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
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
    
    private Cliente cliente;
    private List<ItemPedidoDTO> itens;
    private FormaPagamento formaPagamento;
    private String numeroCartao;
    private BigDecimal valorPagamento;
}
