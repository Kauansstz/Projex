package com.kauan.projex.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kauan.projex.model.InfoUser;
import com.kauan.projex.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class InfoUserService {

    @Autowired
    private UsuarioRepository userRepository; // Nome que você definiu no repositório

    // Caminho onde as fotos serão salvas (fora do target para não sumirem no restart)
    private final String uploadDir = "uploads/profiles/";

    @Transactional
    public void atualizarPerfil(InfoUser formUser, MultipartFile file) throws IOException {
        // 1. Busca o usuário persistido para garantir consistência
        InfoUser usuarioBanco = userRepository.findById(formUser.getId())
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuarioBanco.setName(formUser.getName());
        usuarioBanco.setCargo(formUser.getCargo());
        usuarioBanco.setEmpresa(formUser.getEmpresa());
        usuarioBanco.setTelefone(formUser.getTelefone());
        usuarioBanco.setDescricao(formUser.getDescricao());
        usuarioBanco.setSobre(formUser.getSobre());
        usuarioBanco.setTecnologia(formUser.getTecnologia());
        usuarioBanco.setAtualizadoEm(LocalDateTime.now());

        if (file != null && !file.isEmpty()) {
            if (usuarioBanco.getFotoPerfil() != null) {
                Path fotoAntiga = Paths.get(usuarioBanco.getFotoPerfil().substring(1));
                Files.deleteIfExists(fotoAntiga);
            }

            String nomeArquivo = salvarArquivo(file);
            usuarioBanco.setFotoPerfil("/" + uploadDir + nomeArquivo);
        }
        userRepository.save(usuarioBanco);
    }

    private String salvarArquivo(MultipartFile file) throws IOException {
        // Cria o diretório se não existir
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Gera um nome único para evitar sobrescrever fotos com mesmo nome
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ioe) {
            throw new IOException("Não foi possível salvar o arquivo: " + fileName, ioe);
        }
    }
}
