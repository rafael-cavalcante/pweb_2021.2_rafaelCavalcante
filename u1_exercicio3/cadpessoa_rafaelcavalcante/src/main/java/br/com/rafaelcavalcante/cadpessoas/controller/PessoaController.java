package br.com.rafaelcavalcante.cadpessoas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PessoaController {
    
    @GetMapping
    public String index(){
        return "index.html";
    }
}
