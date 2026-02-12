/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.EducacionDto;
import com.cv_personal.backend.dto.EducacionHerramientasDto;
import com.cv_personal.backend.dto.HerramientaRequestDto;
import com.cv_personal.backend.mapper.EducacionMapper;
import com.cv_personal.backend.model.Educacion;
import com.cv_personal.backend.model.Herramienta;
import com.cv_personal.backend.model.Persona;
import com.cv_personal.backend.repository.IEducacionRepository;
import com.cv_personal.backend.repository.IHerramientaRepository;
import com.cv_personal.backend.repository.IPersonaRepository;
import com.cv_personal.backend.util.FileUploadUtil; // Import FileUploadUtil
import jakarta.annotation.PostConstruct; // Import PostConstruct
import java.io.IOException; // Import IOException
import java.nio.file.Files; // Import Files
import java.nio.file.Path; // Import Path
import java.nio.file.Paths; // Import Paths
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // Import Value
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile; // Import MultipartFile
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class EducacionService implements IEducacionService{
    
    private static final Logger logger = LoggerFactory.getLogger(EducacionService.class);
    
    @Autowired
    private IEducacionRepository educRepository;
    
    @Autowired
    private EducacionMapper educMap;

    @Autowired
    private IHerramientaService herramientaService;

    @Autowired
    private IPersonaRepository personaRepository;    
    
    @Autowired
    private IHerramientaRepository herramientaRepository;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Value("${uploads.directory}")
    private String uploadDir;
    
    @PostConstruct
    public void init() {
        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                logger.info("Created uploads directory: {}", uploadDir);
            }
        } catch (IOException e) {
            logger.error("Could not create uploads directory: {}", uploadDir, e);
            throw new RuntimeException("Could not create uploads directory", e);
        }
    }
    
    @Override
    public EducacionDto saveEducacion(Educacion educacion) {
        Educacion educacionSave = educRepository.save(educacion);
        EducacionDto educacionDto = educMap.toDto(educacionSave);
        return educacionDto;
    }

    @Override
    public List<EducacionDto> getEducacion() {
        List<Educacion> educaciones = educRepository.findAll();
        List<EducacionDto> listEducacionDto = new ArrayList<>();
        
        for(Educacion educacion : educaciones){
            listEducacionDto.add(educMap.toDto(educacion));
        }
        
        return listEducacionDto;
    }

    @Override
    public EducacionDto findEducacion(Long id) {
        Educacion educacion = educRepository.findById(id).orElse(null);
        EducacionDto educacionDto = educMap.toDto(educacion);
        return educacionDto;
    }

    @Override
    @Transactional
    public void deleteEducacion(Long id) {
        Educacion educacion = educRepository.findById(id)
                                    .orElseThrow(() -> new RuntimeException("Educacion not found with ID: " + id));
        
        // Remove associations with Herramienta entities
        for (Herramienta herramienta : new ArrayList<>(educacion.getHerramientas())) {
            herramienta.getEstudios().remove(educacion);
            herramientaRepository.save(herramienta); // Save Herramienta to update the join table
        }
        educacion.getHerramientas().clear(); // Clear the collection on the Educacion side as well

        if (educacion.getLogo_imagen() != null && !educacion.getLogo_imagen().isEmpty()) {
            try {
                fileUploadUtil.deleteFile(uploadDir, educacion.getLogo_imagen());
                logger.info("Deleted old logo image: {}", educacion.getLogo_imagen());
            } catch (IOException e) {
                logger.warn("Could not delete old logo image {}: {}", educacion.getLogo_imagen(), e.getMessage());
            }
        }
        educRepository.deleteById(id);
    }

    @Override
    public Educacion updateEducacion(Long id) {
        Educacion educacion = educRepository.findById(id).orElse(null);
        return educacion;
    }

    @Override
    @Transactional
    public EducacionDto addHerramientasToEducacion(EducacionHerramientasDto dto) {
        Educacion educacion = educRepository.findById(dto.getEducacionId())
                                    .orElseThrow(() -> new RuntimeException("Educacion not found with ID: " + dto.getEducacionId()));

        for (HerramientaRequestDto herramientaDto : dto.getHerramientas()) {
            Herramienta herramienta;
            if (herramientaDto.getId() != null) {
                // Herramienta already exists, fetch it from repository
                herramienta = herramientaRepository.findById(herramientaDto.getId())
                        .orElseThrow(() -> new RuntimeException("Herramienta not found with ID: " + herramientaDto.getId()));
            } else {
                // Herramienta does not exist, create it
                herramienta = new Herramienta();
                herramienta.setNombre(herramientaDto.getNombre());
                herramienta.setVersion(herramientaDto.getVersion());
                herramientaService.saveHerramienta(herramienta); // Save and update its ID
            }

            // Establish ManyToMany relationship
            educacion.getHerramientas().add(herramienta);
            herramienta.getEstudios().add(educacion); // Maintain bidirectional consistency
        }
        
        // Save the updated Educacion which will cascade the relationship changes if properly configured
        Educacion updatedEducacion = educRepository.save(educacion);
        return educMap.toDto(updatedEducacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EducacionDto> getEducacionByPersonaId(Long personaId) {
        Persona persona = personaRepository.findById(personaId)
                                .orElseThrow(() -> new RuntimeException("Persona not found with ID: " + personaId));
        
        List<EducacionDto> listEducacionDto = new ArrayList<>();
        for (Educacion educacion : persona.getEstudios()) {
            listEducacionDto.add(educMap.toDto(educacion));
        }
        return listEducacionDto;
    }
    
    @Override
    @Transactional
    public EducacionDto updateLogoImage(Long id, MultipartFile file) {
        Educacion educacion = educRepository.findById(id)
                                    .orElseThrow(() -> new RuntimeException("Educacion not found with ID: " + id));

        // Delete old image if it exists
        if (educacion.getLogo_imagen() != null && !educacion.getLogo_imagen().isEmpty()) {
            try {
                fileUploadUtil.deleteFile(uploadDir, educacion.getLogo_imagen());
                logger.info("Deleted old logo image: {}", educacion.getLogo_imagen());
            } catch (IOException e) {
                logger.warn("Could not delete old logo image {}: {}", educacion.getLogo_imagen(), e.getMessage());
            }
        }

        if (file != null && !file.isEmpty()) {
            try {
                String newLogoPath = fileUploadUtil.saveFile(uploadDir, file);
                educacion.setLogo_imagen(newLogoPath);
                logger.info("Updated logo image for Educacion ID {}: {}", id, newLogoPath);
            } catch (IOException e) {
                logger.error("Could not save logo image for Educacion ID {}: {}", id, e.getMessage());
                throw new RuntimeException("Could not save logo image", e);
            }
        } else {
            educacion.setLogo_imagen(null);
            logger.info("Set logo image to null for Educacion ID {} as no file was provided.", id);
        }

        Educacion updatedEducacion = educRepository.save(educacion);
        return educMap.toDto(updatedEducacion);
    }
}


