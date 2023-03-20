package br.com.rafaelcavalcante.biritashop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }
    
    @GetMapping("/listar")
    public ModelAndView listarProdutos() {
        List<Produto> produtos = this.produtoRepository.findAll();
        ModelAndView mav = new ModelAndView("/produto/listarProdutos");
        mav.addObject("produtos", produtos);
        return mav;
    }

    @GetMapping("/adicionar")
    public ModelAndView formAdicionarProduto() {
        ModelAndView mav = new ModelAndView("/produto/adicionarProduto");
        mav.addObject(new Produto());
        return mav;
    }

    @PostMapping("/adicionar")
    public String adicionarProduto(Produto produto) {
        this.produtoRepository.save(produto);
        return "redirect:/produto/listar";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView formEditarProduto(@PathVariable("id") Long id) {
        Produto produto = this.produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
        ModelAndView mav = new ModelAndView("/produto/editarProduto");
        mav.addObject(produto);
        return mav;
    }

    @PostMapping("/editar/{id}")
    public String editarProduto(@PathVariable("id") Long id, Produto produto) {
        this.produtoRepository.save(produto);
        return "redirect:/produto/listar";
    }
}
