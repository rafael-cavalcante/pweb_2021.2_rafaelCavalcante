package br.com.rafaelcavalcante.cadpessoas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.rafaelcavalcante.cadpessoas.model.Pessoa;
import br.com.rafaelcavalcante.cadpessoas.repository.PessoaRepository;

@Controller
@RequestMapping("/")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepo;

    public PessoaController(PessoaRepository pessoaRepo) {
        this.pessoaRepo = pessoaRepo;
    }

    @GetMapping
    public String index() {
        return "index.html";
    }

    @GetMapping("/listarPessoas")
    public ModelAndView listarPessoas() {
        List<Pessoa> pessoas = this.pessoaRepo.findAll();
        ModelAndView mav = new ModelAndView("listarPessoas");
        mav.addObject("pessoas", pessoas);
        return mav;
    }

    @GetMapping("/adicionarPessoa")
    public ModelAndView formAdicionarPessoa() {
        ModelAndView mav = new ModelAndView("adicionarPessoa");
        mav.addObject(new Pessoa());
        return mav;
    }

    @PostMapping("/adicionarPessoa")
    public String adicionarPessoa(Pessoa pessoa) {
        this.pessoaRepo.save(pessoa);
        return "redirect:/listarPessoas";
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerPessoa(@PathVariable("id") Long id) {
        Pessoa pessoa = this.pessoaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
        this.pessoaRepo.delete(pessoa);
        return new ModelAndView("redirect:/listarPessoas");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarPessoa(@PathVariable("id") Long id) {
        Pessoa pessoa = this.pessoaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Inválido " + id));
        ModelAndView mav = new ModelAndView("editarPessoa");
        mav.addObject(pessoa);
        return mav;
    }

    @PostMapping("/editar/{id}")
    public String editarPessoa(@PathVariable("id") Long id, Pessoa pessoa) {
        this.pessoaRepo.save(pessoa);
        return "redirect:/listarPessoas";
    }
}
