package br.com.rafaelcavalcante.biritashop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.rafaelcavalcante.biritashop.model.enums.Embalagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "marca")
    private String marca;
    @Column(name = "volume")
    private BigDecimal volume;
    @Column(name = "tipo_embalagem")
    private Embalagem embalagem;
    @Column(name = "preco_compra")
    private BigDecimal precoCompra;
    @Column(name = "preco_venda")
    private BigDecimal precoVenda;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCadastro;
    @Lob
    @Column(name = "foto")
    private byte[] foto;

    public String getFotoBase64() {
        return Base64.getEncoder().encodeToString(this.foto);
    }
}
