package br.com.rafaelcavalcante.biritashop.configs;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.repository.ClienteRepository;

@Service
@Transactional
public class ClienteDetailsService implements UserDetailsService {

    private ClienteRepository clienteRepository;

    public ClienteDetailsService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = this.clienteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente " + username + " Não Encontrado"));
        return new User(cliente.getUsername(), cliente.getPassword(), true, true, true, true, cliente.getAuthorities());
    }
}