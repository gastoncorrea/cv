/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.HerramientaDto;
import com.cv_personal.backend.mapper.HerramientaMapper;
import com.cv_personal.backend.model.Herramienta;
import com.cv_personal.backend.repository.IHerramientaRepository;
import com.cv_personal.backend.util.FileUploadUtil; // Import FileUploadUtil
import java.io.IOException; // Import IOException
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private FileUploadUtil fileUploadUtil;
    
    @PostConstruct
    public void init() {
        try {
            Path path = Paths.get(uploadsDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                logger.info("Created uploads directory: {}", uploadsDir);
            }
        } catch (IOException e) {
            logger.error("Could not create uploads directory: {}", uploadsDir, e);
            throw new RuntimeException("Could not create uploads directory", e);
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
            return null;
        }
        return herrMapper.toDto(herramienta);
    }

    @Override
    public void deleteHerramienta(Long id) {
        Herramienta herramienta = herramientaRepo.findById(id)
                                    .orElseThrow(() -> new RuntimeException("Herramienta no encontrada con ID: " + id));
        
        if (herramienta.getLogo() != null && !herramienta.getLogo().isEmpty()) {
            try {
                fileUploadUtil.deleteFile(uploadsDir, herramienta.getLogo());
                logger.info("Deleted old logo image: {}", herramienta.getLogo());
            } catch (IOException e) {
                logger.warn("Could not delete old logo image {}: {}", herramienta.getLogo(), e.getMessage());
            }
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
        // Logo is handled by updateLogoImage method
        // if (herramientaDto.getLogo() != null && !herramientaDto.getLogo().isEmpty()) {
        //     herramienta.setLogo(herramientaDto.getLogo());
        // }

        Herramienta updatedHerramienta = herramientaRepo.save(herramienta);
        logger.info("Herramienta con ID {} actualizada con éxito.", id);
        return updatedHerramienta;
    }

    @Override
    public HerramientaDto updateLogoImage(Long id, MultipartFile file) {
        Herramienta herramienta = herramientaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Herramienta no encontrada con ID: " + id));

        // Delete old image if it exists
        if (herramienta.getLogo() != null && !herramienta.getLogo().isEmpty()) {
            try {
                fileUploadUtil.deleteFile(uploadsDir, herramienta.getLogo());
                logger.info("Deleted old logo image: {}", herramienta.getLogo());
            } catch (IOException e) {
                logger.warn("Could not delete old logo image {}: {}", herramienta.getLogo(), e.getMessage());
            }
        }

        if (file != null && !file.isEmpty()) {
            try {
                String newLogoPath = fileUploadUtil.saveFile(uploadsDir, file);
                String fileUrl = "/uploads/" + newLogoPath; // Construct the URL
                herramienta.setLogo(fileUrl);
                logger.info("Updated logo image for Herramienta ID {}: {}", id, fileUrl);
            } catch (IOException e) {
                logger.error("Could not save logo image for Herramienta ID {}: {}", id, e.getMessage());
                throw new RuntimeException("Could not save logo image", e);
            }
        } else {
            herramienta.setLogo(null);
            logger.info("Set logo image to null for Herramienta ID {} as no file was provided.", id);
        }

        herramientaRepo.save(herramienta);
        return herrMapper.toDto(herramienta);
    }
}
