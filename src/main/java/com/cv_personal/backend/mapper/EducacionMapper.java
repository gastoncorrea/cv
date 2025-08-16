/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.mapper;

import com.cv_personal.backend.dto.EducacionDto;
import com.cv_personal.backend.model.Educacion;
import org.springframework.stereotype.Component;

@Component
public class EducacionMapper {
    
    public EducacionDto toDto(Educacion educacion){
        
        EducacionDto educacionDto = new EducacionDto();
        educacionDto.setId_educacion(educacion.getId_educacion());
        educacionDto.setNombre_institucion(educacion.getNombre_institucion());
        educacionDto.setLogo_imagen(educacion.getLogo_imagen());
        educacionDto.setFecha_inicio(educacion.getFecha_inicio());
        educacionDto.setFecha_fin(educacion.getFecha_fin());
        educacionDto.setTitulo(educacion.getTitulo());
        educacionDto.setUrl_titulo(educacion.getUrl_titulo());
        
        return educacionDto;
    }
}
