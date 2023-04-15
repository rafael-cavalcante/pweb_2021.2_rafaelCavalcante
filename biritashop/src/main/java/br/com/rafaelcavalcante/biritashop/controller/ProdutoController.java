package br.com.rafaelcavalcante.biritashop.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.biritashop.exceptios.ImagemConstrucaoException;
import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    private ProdutoRepository produtoRepo;

    public ProdutoController(ProdutoRepository produtoRepo) {
        this.produtoRepo = produtoRepo;
    }

    @GetMapping("/listar")
    public ModelAndView listarProdutos() {
        List<Produto> produtos = this.produtoRepo.findAll();
        return new ModelAndView("/produto/listarProdutos")
                .addObject("produtos", produtos);
    }

    @GetMapping("/adicionar")
    public ModelAndView formAdicionarProduto() {
        return new ModelAndView("/produto/adicionarProduto")
                .addObject(new Produto());
    }

    @PostMapping("/adicionar")
    @Transactional
    public String adicionarProduto(@RequestParam("imagem") MultipartFile imagem, Produto produto) {
        if(!imagem.isEmpty()){
			byte[] bytes = new byte[(int) imagem.getSize()];
			try {
				bytes = imagem.getBytes();
			} catch (Exception exception) {
				System.out.println("opa deu errado" + exception);
			}
			produto.setFoto(bytes);
		}
        produto.setDataCadastro(LocalDate.now());
        this.produtoRepo.save(produto);
        return "redirect:/produto/listar";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView formEditarProduto(@PathVariable("id") Long id) {
        Produto produto = this.produtoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
        return new ModelAndView("/produto/editarProduto")
                .addObject(produto);
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
