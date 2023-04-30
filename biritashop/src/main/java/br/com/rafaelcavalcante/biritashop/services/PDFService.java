package br.com.rafaelcavalcante.biritashop.services;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import br.com.rafaelcavalcante.biritashop.model.Cliente;

@Service
public class PDFService {

    @Autowired
    private TemplateEngine templateEngine;

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

    public void downloadPDF(List<Cliente> clientes, HttpServletResponse response)
            throws IOException, DocumentException {

        Context context = new Context();
        context.setVariable("message", "Primeiro PDF");
        context.setVariable("clientes", clientes);

        String processorHTML = templateEngine.process("template", context);

        ByteArrayOutputStream pdfBuffer = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(processorHTML);
        renderer.layout();
        renderer.createPDF(pdfBuffer);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=documento.pdf");
        response.setContentLength(pdfBuffer.size());
        response.getOutputStream().write(pdfBuffer.toByteArray());
        response.getOutputStream().flush();

    }

    public void download(List<Cliente> clientes, HttpServletResponse response) throws DocumentException, IOException {
        Context context = new Context();
        context.setVariable("clientes", clientes);

        String html = templateEngine.process("template", context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=documento.pdf");
        response.setContentLength(outputStream.size());
        response.getOutputStream().write(outputStream.toByteArray());
        response.getOutputStream().flush();
    }
}