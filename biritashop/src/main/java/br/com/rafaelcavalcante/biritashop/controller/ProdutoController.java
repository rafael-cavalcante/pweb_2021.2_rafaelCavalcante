package br.com.rafaelcavalcante.biritashop.controller;

import java.time.LocalDate;
import java.util.List;

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

    private ProdutoRepository produtoRepo;

    public ProdutoController(ProdutoRepository produtoRepo){
        this.produtoRepo = produtoRepo;
    }
    
    @GetMapping("/listar")
    public ModelAndView listarProdutos() {
        List<Produto> produtos = this.produtoRepo.findAll();
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
        produto.setDataCadastro(LocalDate.now());
        this.produtoRepo.save(produto);
        return "redirect:/produto/listar";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView formEditarProduto(@PathVariable("id") Long id) {
        Produto produto = this.produtoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
        ModelAndView mav = new ModelAndView("/produto/editarProduto");
        mav.addObject(produto);
        return mav;
    }

    @PostMapping("/editar/{id}")
    public String editarProduto(@PathVariable("id") Long id, Produto produto) {
        this.produtoRepo.save(produto);
        return "redirect:/produto/listar";
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerProduto(@PathVariable("id") Long id) {
        Produto produto = this.produtoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
        this.produtoRepo.delete(produto);
        return new ModelAndView("redirect:/produto/listar");
    }
}
