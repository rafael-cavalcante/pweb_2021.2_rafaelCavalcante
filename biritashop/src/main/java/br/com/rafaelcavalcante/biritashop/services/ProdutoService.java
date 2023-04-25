package br.com.rafaelcavalcante.biritashop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepo;

    public ProdutoService(ProdutoRepository produtoRepo){
        this.produtoRepo = produtoRepo;
    }
    
    public Page<Produto> listarPaginaProdutos(Pageable pageable){
        return this.produtoRepo.findAll(pageable);
    }
}
