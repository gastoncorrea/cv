/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.mapper;

import com.cv_personal.backend.dto.PersonaDto;
import com.cv_personal.backend.model.Persona;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {
    
    public PersonaDto toDto(Persona persona){
        PersonaDto personaDto = new PersonaDto();
        personaDto.setId_persona(persona.getId_persona());
        personaDto.setNombre(persona.getNombre());
        personaDto.setApellido(persona.getApellido());
        personaDto.setImagen_perfil(persona.getImagen_perfil());
        personaDto.setDescripcion_mi(persona.getDescripcion_mi());
        personaDto.setFecha_nacimiento(persona.getFecha_nacimiento());
        personaDto.setNum_celular(persona.getNum_celular());
        
        personaDto.setContactos(persona.getContacto());
        personaDto.setResidencias(persona.getResidencia());
        
        return personaDto;
    }
}
