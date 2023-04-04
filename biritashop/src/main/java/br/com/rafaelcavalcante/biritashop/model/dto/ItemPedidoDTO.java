package br.com.rafaelcavalcante.biritashop.model.dto;

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
    private Long quantidade;

}
