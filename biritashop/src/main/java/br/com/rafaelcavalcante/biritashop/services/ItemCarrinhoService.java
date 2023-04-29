package br.com.rafaelcavalcante.biritashop.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rafaelcavalcante.biritashop.model.Carrinho;
import br.com.rafaelcavalcante.biritashop.model.ItemCarrinho;
import br.com.rafaelcavalcante.biritashop.repository.ItemCarrinhoRepository;

@Service
public class ItemCarrinhoService {
	
	@Autowired
	private ItemCarrinhoRepository itemCarrinhoRepository;

	@Transactional
	public void removerItensCarrinho(Carrinho carrinho) {
        List<ItemCarrinho> itensCarrinho = this.itemCarrinhoRepository.findByCarrinhoId(carrinho.getId());
        
        this.itemCarrinhoRepository.deleteAll(itensCarrinho);
	}
}
