/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.PersonaDto;
import com.cv_personal.backend.model.Persona;
import java.util.List;


public interface IPersonaService {
    
    public PersonaDto savePersona(Persona persona);
    
    public List<PersonaDto> getPersonas();
    
    public PersonaDto findPersona(Long id);
    
    public Persona updatePersona(Long id);
    
    public void deletePersona(Long id);
    
}
