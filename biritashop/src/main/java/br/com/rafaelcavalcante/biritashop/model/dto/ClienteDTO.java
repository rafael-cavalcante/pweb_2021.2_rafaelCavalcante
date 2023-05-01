package br.com.rafaelcavalcante.biritashop.model.dto;

import br.com.rafaelcavalcante.biritashop.model.enums.Genero;
import lombok.Data;

@Data
public class ClienteDTO {

    private String username;
    private String password;
    private String nomeCompleto;
    private Genero genero;
    private String cidade;
    private String CEP;
    private String email;
    private String telefone;

}
