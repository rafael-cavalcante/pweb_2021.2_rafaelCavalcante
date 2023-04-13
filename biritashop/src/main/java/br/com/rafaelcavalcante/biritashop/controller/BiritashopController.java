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
        return new ModelAndView("index");
    }

    @GetMapping("/public/sobre")
    public ModelAndView sobre() {
        return new ModelAndView("/public/sobre")
                .addObject("pagSobre", true);
    }

    @GetMapping("/public/contato")
    public ModelAndView contato() {
        return new ModelAndView("/public/contato")
                .addObject("pagContato", true);
    }

    @GetMapping("/public/layout")
    public ModelAndView layout() {
        return new ModelAndView("/public/layout");
    }
}