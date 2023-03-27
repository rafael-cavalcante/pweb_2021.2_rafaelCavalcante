package br.com.rafaelcavalcante.biritashop.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    private String nomeCompleto;
    private String genero;
    private String telefone;
    private String cidade;
    private String CEP;
    @OneToMany(mappedBy = "cliente")
    private List<Dependente> dependentes;
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
}
