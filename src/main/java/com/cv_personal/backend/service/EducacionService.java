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
import com.cv_personal.backend.model.Persona; // Import Persona
import com.cv_personal.backend.repository.IEducacionRepository;
import com.cv_personal.backend.repository.IHerramientaRepository;
import com.cv_personal.backend.repository.IPersonaRepository; // Import IPersonaRepository
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class EducacionService implements IEducacionService{
    
    @Autowired
    private IEducacionRepository educRepository;
    
    @Autowired
    private EducacionMapper educMap;

    @Autowired
    private IHerramientaService herramientaService; // Inject IHerramientaService

    @Autowired
    private IPersonaRepository personaRepository; // Inject IPersonaRepository    
    
    @Autowired
    private IHerramientaRepository herramientaRepository;
    
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
    public void deleteEducacion(Long id) {
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
    @Transactional(readOnly = true) // Add Transactional and readOnly for fetching
    public List<EducacionDto> getEducacionByPersonaId(Long personaId) {
        Persona persona = personaRepository.findById(personaId)
                                .orElseThrow(() -> new RuntimeException("Persona not found with ID: " + personaId));
        
        List<EducacionDto> listEducacionDto = new ArrayList<>();
        for (Educacion educacion : persona.getEstudios()) { // Access estudios directly
            listEducacionDto.add(educMap.toDto(educacion));
        }
        return listEducacionDto;
    }
}
