/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.PersonaDto;
import com.cv_personal.backend.mapper.PersonaMapper;
import com.cv_personal.backend.model.Persona;
import com.cv_personal.backend.repository.IPersonaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaService implements IPersonaService  {
    
    @Autowired
    private IPersonaRepository personaRep;
    
    @Autowired
    private PersonaMapper personaMap;
    
    @Override
    public PersonaDto savePersona(Persona persona) {
       Persona personaSave = personaRep.save(persona);
       PersonaDto personaDto = personaMap.toDto(personaSave);
       return personaDto;
    }

    @Override
    public List<PersonaDto> getPersonas() {
        List<Persona> listPersona = personaRep.findAll();
        List<PersonaDto> listPersonaDto = new ArrayList<>();
        
        for(Persona persona : listPersona){
            listPersonaDto.add(personaMap.toDto(persona));
        }
        return listPersonaDto;
    }

    @Override
    public PersonaDto findPersona(Long id) {
        Persona persona = personaRep.findById(id).orElse(null);
        PersonaDto personaDto = personaMap.toDto(persona);
        return personaDto;
    }

    @Override
    public void deletePersona(Long id) {
        personaRep.deleteById(id);
    }

    @Override
    public Persona updatePersona(Long id) {
        Persona persona = personaRep.findById(id).orElse(null);
        return persona;
    }
    
}
