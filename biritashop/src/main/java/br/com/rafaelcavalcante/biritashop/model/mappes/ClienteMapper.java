package br.com.rafaelcavalcante.biritashop.model.mappes;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.dtos.ClienteDTO;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "carrinho", ignore = true),
            @Mapping(target = "dependentes", ignore = true),
            @Mapping(target = "pedidos", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "authorities", ignore = true)
    })
    Cliente toCliente(ClienteDTO clienteDTO);

    ClienteDTO toClienteDTO(Cliente cliente);

}
