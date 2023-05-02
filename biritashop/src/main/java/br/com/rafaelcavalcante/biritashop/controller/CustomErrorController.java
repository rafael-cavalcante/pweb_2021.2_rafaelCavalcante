package br.com.rafaelcavalcante.biritashop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException exception) {

        return new ModelAndView("/error")
                .addObject("statusCode", response.getStatus())
                .addObject("class", exception.getClass())
                .addObject("message", exception.getMessage())
                .addObject("error", exception.getStackTrace());
    }
}
