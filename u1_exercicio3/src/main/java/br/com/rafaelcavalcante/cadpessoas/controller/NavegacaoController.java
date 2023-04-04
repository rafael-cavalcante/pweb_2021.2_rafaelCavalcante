package br.com.rafaelcavalcante.cadpessoas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavegacaoController {
    
    @GetMapping("/contato")
    public ModelAndView contato() {
        ModelAndView mav = new ModelAndView("contato");
        mav.addObject("active", "contato");
        return mav;
    }

    @GetMapping("/sobre")
    public ModelAndView sobre() {
        ModelAndView mav = new ModelAndView("sobre");
        mav.addObject("active", "sobre");
        return mav;
    }

    @GetMapping("/noticias")
    public ModelAndView noticias() {
        ModelAndView mav = new ModelAndView("noticias");
        mav.addObject("active", "noticias");
        return mav;
    }
}
