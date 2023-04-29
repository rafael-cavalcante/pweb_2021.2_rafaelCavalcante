package br.com.rafaelcavalcante.biritashop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rafaelcavalcante.biritashop.model.Carrinho;
import br.com.rafaelcavalcante.biritashop.repository.CarrinhoRepository;

@Service
public class CarrinhoService {

	@Autowired
	private CarrinhoRepository carrinhoRepository;
	
	public Carrinho criarCarrinho() {
		return this.carrinhoRepository.save(new Carrinho());
	}
}
