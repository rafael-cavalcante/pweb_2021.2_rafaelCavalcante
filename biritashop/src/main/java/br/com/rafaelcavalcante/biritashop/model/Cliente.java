package br.com.rafaelcavalcante.biritashop.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import br.com.rafaelcavalcante.biritashop.model.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
}
