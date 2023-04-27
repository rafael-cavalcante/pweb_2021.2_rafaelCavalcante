package br.com.rafaelcavalcante.biritashop.controller;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.biritashop.model.Produto;
import br.com.rafaelcavalcante.biritashop.model.enums.Embalagem;
import br.com.rafaelcavalcante.biritashop.repository.ProdutoRepository;
import br.com.rafaelcavalcante.biritashop.services.FotoService;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoService fotoService;

    @GetMapping("/listar")
    public ModelAndView listarProdutos() {
        List<Produto> produtos = this.produtoRepository.findAll();
        return new ModelAndView("/produto/listarProdutos")
                .addObject("produtos", produtos);
    }

    @GetMapping("/adicionar")
    public ModelAndView formAdicionarProduto() {
        return new ModelAndView("/produto/adicionarProduto")
                .addObject("embalagens", Embalagem.values())
                .addObject("produto", new Produto());
    }

    @PostMapping("/adicionar")
    @Transactional
    public String adicionarProduto(@RequestParam("imagem") MultipartFile imagem, Produto produto) {
        /*
         * if (!imagem.isEmpty()) {
         * byte[] bytes = new byte[(int) imagem.getSize()];
         * try {
         * bytes = imagem.getBytes();
         * } catch (Exception exception) {
         * System.out.println("Não foi Possível Salvar a Imagem" + exception);
         * }
         * produto.setFoto(bytes);
         * }
         */
        produto.setFoto(this.fotoService.uploadFoto(imagem));
        produto.setDataCadastro(LocalDate.now());
        this.produtoRepository.save(produto);
        return "redirect:/produto/listar";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView formEditarProduto(@PathVariable("id") Long id) {
        Produto produto = this.produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido ID " + id + " Não Encontrado"));
        return new ModelAndView("/produto/editarProduto")
                .addObject("embalagens", Embalagem.values())
                // .addObject("fotoP", produto.getFotoBase64())
                .addObject("produto", produto);
    }

    @PostMapping("/editar/{id}")
    @Transactional
    public String editarProduto(@RequestParam("imagem") MultipartFile imagem, @PathVariable("id") Long id,
            Produto produto) {
        Produto produtoOriginal = this.produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido ID " + id + " Não Encontrado"));
        if (!imagem.isEmpty()) {
            /*
             * byte[] bytes = new byte[(int) imagem.getSize()];
             * try {
             * bytes = imagem.getBytes();
             * } catch (Exception exception) {
             * System.out.println("Não foi Possível Salvar a Imagem" + exception);
             * }
             */
            produto.setFoto(this.fotoService.uploadFoto(imagem));
        } else {
            produto.setFoto(produtoOriginal.getFoto());
        }
        produto.setDataCadastro(produtoOriginal.getDataCadastro());
        this.produtoRepository.save(produto);
        return "redirect:/produto/listar";
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerProduto(@PathVariable("id") Long id) {
        Produto produto = this.produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido ID " + id + " Não Encontrado"));
        this.produtoRepository.delete(produto);
        return new ModelAndView("redirect:/produto/listar");
    }

    @GetMapping("/visualizar/{produtoId}")
    public ModelAndView visualizarProduto(@PathVariable("produtoId") Long produtoId) {
        Produto produto = this.produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto ID " + produtoId + " Não Encontrado"));
        return new ModelAndView("/produto/visualizarProduto")
                .addObject("produto", produto);
    }
}
