package br.com.rafaelcavalcante.biritashop.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @GetMapping()
    public ModelAndView handleError(HttpServletRequest request, Exception exception) {
        return new ModelAndView("/error")
                .addObject("class", exception.getClass())
                .addObject("message", exception.getMessage())
                .addObject("error", exception.getStackTrace());
    }
}
