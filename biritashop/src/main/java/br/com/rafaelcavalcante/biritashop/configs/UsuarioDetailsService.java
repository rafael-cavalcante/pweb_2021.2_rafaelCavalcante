package br.com.rafaelcavalcante.biritashop.configs;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.rafaelcavalcante.biritashop.model.Usuario;
import br.com.rafaelcavalcante.biritashop.repository.UsuarioRepository;


@Service
@Transactional
public class UsuarioDetailsService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("USUÁRIO NÃO ENCONTRADO: " + username));

        return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true,
                usuario.getAuthorities());
    }

}