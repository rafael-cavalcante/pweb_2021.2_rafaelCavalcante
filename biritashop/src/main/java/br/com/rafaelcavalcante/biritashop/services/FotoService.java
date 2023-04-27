package br.com.rafaelcavalcante.biritashop.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoService {

    @Value("${image.upload.directory}")
    private String imageUploadDirectory; // diretório onde as imagens serão salvas

    public String uploadFoto(MultipartFile imagem) {
        try {

            Path uploadPath = Paths.get(imageUploadDirectory);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Salva a imagem no diretório especificado
            String filename = StringUtils.cleanPath(imagem.getOriginalFilename());
            Path targetPath = uploadPath.resolve(filename);
            
            Files.copy(imagem.getInputStream(), targetPath);

            return imagem.getOriginalFilename();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
