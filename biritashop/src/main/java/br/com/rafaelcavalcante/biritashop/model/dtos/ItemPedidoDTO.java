package br.com.rafaelcavalcante.biritashop.model.dtos;

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
    private ProdutoDTO produto;
    private Long quantidade;

}
