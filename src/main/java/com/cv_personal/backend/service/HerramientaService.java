/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.HerramientaDto;
import com.cv_personal.backend.mapper.HerramientaMapper;
import com.cv_personal.backend.model.Herramienta;
import com.cv_personal.backend.repository.IHerramientaRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HerramientaService implements IHerramientaService {
    
    private static final Logger logger = LoggerFactory.getLogger(HerramientaService.class);
    
    @Value("${uploads.directory}")
    private String uploadsDir;
    
    @Autowired
    private IHerramientaRepository herramientaRepo;
    
    @Autowired
    private HerramientaMapper herrMapper;
    
    @PostConstruct
    public void init() {
        try {
            logger.info("uploadsDir configurado a: {}", uploadsDir); // Log the value for debugging
            Files.createDirectories(Paths.get(uploadsDir));
            logger.info("Directorio de carga de logos inicializado en: {}", uploadsDir);
        } catch (IOException e) {
            logger.error("No se pudo crear el directorio de carga de logos: {}", uploadsDir, e);
            throw new RuntimeException("No se pudo inicializar el directorio de carga", e);
        }
    }
    
    @Override
    public HerramientaDto saveHerramienta(Herramienta herramienta) {
        Herramienta herramientaSave = herramientaRepo.save(herramienta);
        HerramientaDto herramientaDto = herrMapper.toDto(herramientaSave);
        return herramientaDto;
    }

    @Override
    public List<HerramientaDto> getHerramienta() {
        List<Herramienta> herramientas = herramientaRepo.findAll();
        List<HerramientaDto> listHerramientas = new ArrayList<>();
        
        for (Herramienta herramienta : herramientas){
            listHerramientas.add(herrMapper.toDto(herramienta));
        }
        return listHerramientas;
    }

    @Override
    public HerramientaDto findHerramienta(Long id) {
        Herramienta herramienta = herramientaRepo.findById(id).orElse(null);
        if (herramienta == null) {
            logger.warn("Herramienta con ID {} no encontrada.", id);
            return null; // Or throw a specific exception
        }
        return herrMapper.toDto(herramienta);
    }

    @Override
    public void deleteHerramienta(Long id) {
        if (!herramientaRepo.existsById(id)) {
            logger.warn("Intento de eliminar Herramienta con ID {} que no existe.", id);
            throw new RuntimeException("Herramienta no encontrada con ID: " + id);
        }
        herramientaRepo.deleteById(id);
        logger.info("Herramienta con ID {} eliminada con éxito.", id);
    }

    @Override
    public Herramienta updateHerramienta(Long id, HerramientaDto herramientaDto) {
        Herramienta herramienta = herramientaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Herramienta no encontrada con ID: " + id));

        if (herramientaDto.getNombre() != null && !herramientaDto.getNombre().isEmpty()) {
            herramienta.setNombre(herramientaDto.getNombre());
        }
        if (herramientaDto.getVersion() != null && !herramientaDto.getVersion().isEmpty()) {
            herramienta.setVersion(herramientaDto.getVersion());
        }
        if (herramientaDto.getDescripcion() != null && !herramientaDto.getDescripcion().isEmpty()) {
            herramienta.setDescripcion(herramientaDto.getDescripcion());
        }
        if (herramientaDto.getUrl() != null && !herramientaDto.getUrl().isEmpty()) {
            herramienta.setUrl(herramientaDto.getUrl());
        }
        if (herramientaDto.getLogo() != null && !herramientaDto.getLogo().isEmpty()) {
            herramienta.setLogo(herramientaDto.getLogo());
        }

        Herramienta updatedHerramienta = herramientaRepo.save(herramienta);
        logger.info("Herramienta con ID {} actualizada con éxito.", id);
        return updatedHerramienta;
    }

    @Override
    public HerramientaDto updateLogoImage(Long id, MultipartFile file) {
        Herramienta herramienta = herramientaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Herramienta no encontrada con ID: " + id));

        if (file.isEmpty()) {
            throw new RuntimeException("No se ha seleccionado ningún archivo para cargar.");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            Path filePath = Paths.get(uploadsDir + File.separator + uniqueFilename);
            Files.copy(file.getInputStream(), filePath);

            herramienta.setLogo("/uploads/" + uniqueFilename); // Store the relative path including /uploads/
            herramientaRepo.save(herramienta);
            logger.info("Logo para Herramienta con ID {} cargado y actualizado con éxito: {}", id, uniqueFilename);
            return herrMapper.toDto(herramienta);
        } catch (IOException e) {
            logger.error("Error al cargar el logo para la Herramienta con ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al cargar el archivo: " + e.getMessage());
        }
    }
}
