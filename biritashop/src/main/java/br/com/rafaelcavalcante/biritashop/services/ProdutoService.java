package br.com.rafaelcavalcante.biritashop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Page<Produto> listarPaginaProdutos(Pageable pageable) {
        return this.produtoRepository.findAll(pageable);
    }

    public Page<Produto> listarPaginaProdutosNome(String nome, Pageable pageable) {
        return this.produtoRepository.findByNomeContainingIgnoreCaseOrMarcaContainingIgnoreCase(nome, nome, pageable);
    }
}
