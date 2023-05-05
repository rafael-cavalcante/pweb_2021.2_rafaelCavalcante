package br.com.rafaelcavalcante.biritashop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rafaelcavalcante.biritashop.model.Dependente;
import br.com.rafaelcavalcante.biritashop.repository.DependenteRepository;

@Service
public class DependenteService {
    
    @Autowired
    private DependenteRepository dependenteRepository;

    public List<Dependente> listarDependentes(Long clienteId){
        return this.dependenteRepository.findByCliente_Id(clienteId);
    }
}
