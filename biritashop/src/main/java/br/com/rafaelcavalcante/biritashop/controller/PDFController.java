package br.com.rafaelcavalcante.biritashop.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.DocumentException;

import br.com.rafaelcavalcante.biritashop.model.Cliente;
import br.com.rafaelcavalcante.biritashop.model.Dependente;
import br.com.rafaelcavalcante.biritashop.services.ClienteService;
import br.com.rafaelcavalcante.biritashop.services.DependenteService;
import br.com.rafaelcavalcante.biritashop.services.PDFService;

@Controller
@RequestMapping("/pdf")
public class PDFController {

    @Autowired
    private PDFService pdfService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private DependenteService dependenteService;

    @GetMapping("/dependentes")
    public void gerarPdfDependentes(Principal auth, HttpServletResponse response)
            throws DocumentException, IOException {
        Cliente cliente = this.clienteService.buscarCliente(auth.getName());
        
        List<Dependente> dependentes = this.dependenteService.listarDependentes(cliente.getId());

        this.pdfService.gerarTemplateDependentes(cliente.getNomeCompleto(), dependentes, response);
    }
}
