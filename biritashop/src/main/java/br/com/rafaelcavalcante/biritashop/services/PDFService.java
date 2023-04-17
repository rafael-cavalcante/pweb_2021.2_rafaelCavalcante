package br.com.rafaelcavalcante.biritashop.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import br.com.rafaelcavalcante.biritashop.model.Cliente;

@Service
public class PDFService {

    private TemplateEngine templateEngine;

    public PDFService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void createPDF(List<Cliente> clientes) throws IOException, DocumentException {
        Context context = new Context();
        context.setVariable("message", "Primeiro PDF");
        context.setVariable("clientes", clientes);


        String processorHTML = templateEngine.process("template", context);

        OutputStream outputStream = new FileOutputStream("documento.pdf");
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(processorHTML);
        renderer.layout();
        renderer.createPDF(outputStream, false);
        renderer.finishPDF();
        outputStream.close();
    }
}