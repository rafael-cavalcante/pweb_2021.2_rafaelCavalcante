package br.com.rafaelcavalcante.biritashop.model.mappes;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.dtos.ClienteDTO;

@Mapper
public interface ClienteMapper {
    
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Cliente toCliente(ClienteDTO clienteDTO);

    ClienteDTO toClienteDTO(Cliente cliente);

}
