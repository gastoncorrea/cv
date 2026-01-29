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
import com.cv_personal.backend.model.Persona; // Import Persona
import com.cv_personal.backend.model.Proyecto;
import com.cv_personal.backend.repository.IPersonaRepository; // Import IPersonaRepository
import com.cv_personal.backend.repository.IProyectoRepository;
import com.cv_personal.backend.repository.IHerramientaRepository; // Import IHerramientaRepository
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProyectoService implements IProyectoService{
    
    @Autowired
    private IProyectoRepository proRepo;
    
    @Autowired
    private ProyectoMapper proyMap;

    @Autowired
    private IHerramientaService herramientaService; // Inject IHerramientaService
    
    @Autowired
    private IHerramientaRepository herramientaRepository; // Inject IHerramientaRepository

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
    public void deleteProyecto(Long id) {
        proRepo.deleteById(id);
    }

    @Override
    public Proyecto updateProyecto(Long id) {
        Proyecto proyecto = proRepo.findById(id).orElse(null);
        return proyecto;
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
                herramientaService.saveHerramienta(herramienta); // Save and update its ID
            }

            // Establish ManyToMany relationship
            proyecto.getHerramientas().add(herramienta);
            herramienta.getProyectos().add(proyecto); // Maintain bidirectional consistency
        }
        
        // Save the updated Proyecto which will cascade the relationship changes if properly configured
        Proyecto updatedProyecto = proRepo.save(proyecto);
        return proyMap.toDto(updatedProyecto);
    }

    @Autowired
    private IPersonaRepository personaRepository; // Inject IPersonaRepository

    @Override
    @Transactional(readOnly = true)
    public List<ProyectoDto> getProyectoByPersonaId(Long personaId) {
        Persona persona = personaRepository.findById(personaId)
                                .orElseThrow(() -> new RuntimeException("Persona not found with ID: " + personaId));
        
        List<ProyectoDto> listProyectoDto = new ArrayList<>();
        for (Proyecto proyecto : persona.getProyectos()) { // Access proyectos directly
            listProyectoDto.add(proyMap.toDto(proyecto));
        }
        return listProyectoDto;
    }
}
