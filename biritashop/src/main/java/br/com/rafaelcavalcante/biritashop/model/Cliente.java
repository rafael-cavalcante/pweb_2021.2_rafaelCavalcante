package br.com.rafaelcavalcante.biritashop.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.rafaelcavalcante.biritashop.model.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cliente")
public class Cliente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "genero")
    private Genero genero;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "cep")
    private String CEP;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    private List<Dependente> dependentes;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    private List<Pedido> pedidos;

    @OneToOne
    private Carrinho carrinho;

    @ManyToMany
    @JoinTable(name = "cliente_role", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public Cliente(Long id, String username, String password, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
