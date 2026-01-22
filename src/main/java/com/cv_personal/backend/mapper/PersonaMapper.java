/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.mapper;

import com.cv_personal.backend.dto.ContactoDto;
import com.cv_personal.backend.dto.PersonaDto;
import com.cv_personal.backend.dto.ResidenciaDto;
import com.cv_personal.backend.model.Contacto;
import com.cv_personal.backend.model.Persona;
import com.cv_personal.backend.model.Residencia;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {
    
    @Autowired
    private ContactoMapper contacMap;
    @Autowired
    private ResidenciaMapper resMap;
    
    public PersonaDto toDto(Persona persona){
        PersonaDto personaDto = new PersonaDto();
        personaDto.setId_persona(persona.getId_persona());
        personaDto.setNombre(persona.getNombre());
        personaDto.setApellido(persona.getApellido());
        personaDto.setImagenUrl(persona.getImagenUrl());
        personaDto.setDescripcion_mi(persona.getDescripcion_mi());
        personaDto.setFecha_nacimiento(persona.getFecha_nacimiento());
        personaDto.setNum_celular(persona.getNum_celular());
        
        personaDto.setContactos(listContactoMapper(persona.getContacto()));
        personaDto.setResidencias(listResidenciaMapper(persona.getResidencia()));
        
        return personaDto;
    }
   
    public List<ContactoDto> listContactoMapper(List<Contacto> listContacto){
        
        List<ContactoDto> listContactoDto = new ArrayList<>();
        
        for (Contacto contacto : listContacto){
            listContactoDto.add(contacMap.toDto(contacto));
        }
        
        return listContactoDto;
    }
    
    public List<ResidenciaDto> listResidenciaMapper(List<Residencia> listResidencia){
        
        List<ResidenciaDto> listResidenciaDto = new ArrayList<>();
        
        for (Residencia Residencia : listResidencia){
            listResidenciaDto.add(resMap.toDto(Residencia));
        }
        
        return listResidenciaDto;
    }
}
