package br.com.rafaelcavalcante.biritashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class BiritashopController {

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("/index");
    }

    @GetMapping("/public/sobre")
    public ModelAndView sobre() {
        return new ModelAndView("/public/sobre");
    }

    @GetMapping("/public/contato")
    public ModelAndView contato() {
        return new ModelAndView("/public/contato");
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
}