/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.PersonaDto;
import com.cv_personal.backend.model.Persona;
import com.cv_personal.backend.repository.IPersonaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaService implements IPersonaService  {
    @Autowired
    private IPersonaRepository personaRep;
    
    @Override
    public PersonaDto savePersona(Persona persona) {
        personaRep.save(persona);
    }

    @Override
    public List<PersonaDto> getPersonas() {
        List<Persona> listPersona = personaRep.findAll();
        return listPersona;
    }

    @Override
    public PersonaDto findPersona(Long id) {
        Persona persona = personaRep.findById(id).orElse(null);
        return persona;
    }

    @Override
    public void deletePersona(Long id) {
        personaRep.deleteById(id);
    }
    
}
