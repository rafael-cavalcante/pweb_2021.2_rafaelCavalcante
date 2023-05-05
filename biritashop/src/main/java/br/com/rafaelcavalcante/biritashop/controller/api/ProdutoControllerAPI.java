package br.com.rafaelcavalcante.biritashop.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/produto")
public class ProdutoControllerAPI {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<Page<Produto>> listarProduto(Pageable pageable) {
        Page<Produto> produtos = this.produtoRepository.findAll(pageable);

        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> listarProduto(@PathVariable("id") Long id) {
        Produto produto = this.produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto ID " + id + " Nao Encontrado"));

        return new ResponseEntity<>(produto, HttpStatus.OK);
    }
}
