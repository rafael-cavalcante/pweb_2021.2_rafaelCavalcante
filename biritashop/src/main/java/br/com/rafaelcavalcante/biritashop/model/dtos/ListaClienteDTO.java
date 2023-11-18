package br.com.rafaelcavalcante.biritashop.model.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListaClienteDTO {
    
    private List<ItemClienteDTO> itens;
}
