package br.com.rafaelcavalcante.biritashop.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

            String filename = StringUtils.cleanPath(imagem.getOriginalFilename());

            // Salva a imagem no diretório especificado
            Path targetPath = uploadPath.resolve(filename);
            Files.copy(imagem.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return filename;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removerFoto(String foto) {
        Path imagePath = Paths.get(imageUploadDirectory).resolve(foto);

        try {
            Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
