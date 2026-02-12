/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.ProyectoDto;
import com.cv_personal.backend.dto.ProyectoHerramientasDto;
import com.cv_personal.backend.dto.HerramientaRequestDto;
import com.cv_personal.backend.mapper.ProyectoMapper;
import com.cv_personal.backend.model.Herramienta;
import com.cv_personal.backend.model.Persona;
import com.cv_personal.backend.model.Proyecto;
import com.cv_personal.backend.repository.IPersonaRepository;
import com.cv_personal.backend.repository.IProyectoRepository;
import com.cv_personal.backend.repository.IHerramientaRepository;
import com.cv_personal.backend.util.FileUploadUtil;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProyectoService implements IProyectoService{
    
    private static final Logger logger = LoggerFactory.getLogger(ProyectoService.class);

    @Autowired
    private IProyectoRepository proRepo;
    
    @Autowired
    private ProyectoMapper proyMap;

    @Autowired
    private IHerramientaService herramientaService;
    
    @Autowired
    private IHerramientaRepository herramientaRepository;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private Environment env;

    @Override
    public ProyectoDto saveProyecto(Proyecto proyecto) {
        Proyecto proyectoSave = proRepo.save(proyecto);
        ProyectoDto proyectoDto = proyMap.toDto(proyectoSave);
        return proyectoDto;
    }

    @Override
    public List<ProyectoDto> getProyecto() {
        List<Proyecto> proyectos = proRepo.findAll();
        List<ProyectoDto> listProyectoDto = new ArrayList<>();
        
        for(Proyecto proyecto : proyectos){
            listProyectoDto.add(proyMap.toDto(proyecto));
        }
        return listProyectoDto;
    }

    @Override
    public ProyectoDto findProyecto(Long id) {
        Proyecto proyecto = proRepo.findById(id).orElse(null);
        ProyectoDto proyectoDto = proyMap.toDto(proyecto);
        return proyectoDto;
    }

    @Override
    @Transactional
    public void deleteProyecto(Long id) {
        Proyecto proyecto = proRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto not found with ID: " + id));

        // Disassociate project from all associated tools (Herramienta)
        for (Herramienta herramienta : new HashSet<>(proyecto.getHerramientas())) {
            herramienta.getProyectos().remove(proyecto);
            herramientaRepository.save(herramienta);
        }
        
        proRepo.deleteById(id);
    }

    @Override
    public ProyectoDto updateProyecto(Long id, ProyectoDto proyectoDto) {
        Proyecto proyecto = proRepo.findById(id)
                                .orElseThrow(() -> new RuntimeException("Proyecto not found with ID: " + id));

        if (proyectoDto.getNombre() != null && !proyectoDto.getNombre().isEmpty()) {
            proyecto.setNombre(proyectoDto.getNombre());
        }
        if (proyectoDto.getDescripcion() != null && !proyectoDto.getDescripcion().isEmpty()) {
            proyecto.setDescripcion(proyectoDto.getDescripcion());
        }
        if (proyectoDto.getUrl() != null && !proyectoDto.getUrl().isEmpty()) {
            proyecto.setUrl(proyectoDto.getUrl());
        }
        if (proyectoDto.getInicio() != null) {
            proyecto.setInicio(proyectoDto.getInicio());
        }
        if (proyectoDto.getFin() != null) {
            proyecto.setFin(proyectoDto.getFin());
        }
        if (proyectoDto.getLogo_proyecto() != null && !proyectoDto.getLogo_proyecto().isEmpty()) {
            proyecto.setLogo_proyecto(proyectoDto.getLogo_proyecto());
        }

        return proyMap.toDto(proRepo.save(proyecto));
    }

    @Override
    @Transactional
    public ProyectoDto addHerramientasToProyecto(ProyectoHerramientasDto dto) {
        Proyecto proyecto = proRepo.findById(dto.getProyectoId())
                                .orElseThrow(() -> new RuntimeException("Proyecto not found with ID: " + dto.getProyectoId()));

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
                // Optional: set other fields like description, url, logo if they are part of HerramientaRequestDto
                herramientaService.saveHerramienta(herramienta); // Save and update its ID
            }

            // Establish ManyToMany relationship
            proyecto.getHerramientas().add(herramienta);
            herramienta.getProyectos().add(proyecto); // Maintain bidirectional consistency
        }
        
        Proyecto updatedProyecto = proRepo.save(proyecto);
        return proyMap.toDto(updatedProyecto);
    }

    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProyectoDto> getProyectoByPersonaId(Long personaId) {
        Persona persona = personaRepository.findById(personaId)
                                .orElseThrow(() -> new RuntimeException("Persona not found with ID: " + personaId));
        
        List<ProyectoDto> listProyectoDto = new ArrayList<>();
        for (Proyecto proyecto : persona.getProyectos()) {
            listProyectoDto.add(proyMap.toDto(proyecto));
        }
        return listProyectoDto;
    }

    @Override
    @Transactional
    public ProyectoDto updateLogoImage(Long id, MultipartFile file) {
        Proyecto proyecto = proRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto not found with ID: " + id));

        String uploadDir = env.getProperty("uploads.directory");
        if (uploadDir == null) {
            throw new IllegalStateException("Upload directory is not configured. Please set 'uploads.directory' in application.properties.");
        }

        try {
            String fileName = fileUploadUtil.saveFile(uploadDir, file);
            String fileUrl = "/uploads/" + fileName; // Construct the URL
            proyecto.setLogo_proyecto(fileUrl);
            Proyecto updatedProyecto = proRepo.save(proyecto);
            return proyMap.toDto(updatedProyecto);
        } catch (IOException e) {
            logger.error("Could not save project logo image for ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Could not save project logo image: " + e.getMessage());
        }
    }
}
