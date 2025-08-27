/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.mapper;

import com.cv_personal.backend.dto.EducacionDto;
import com.cv_personal.backend.dto.HerramientaDto;
import com.cv_personal.backend.model.Educacion;
import com.cv_personal.backend.model.Herramienta;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EducacionMapper {
    
    @Autowired
    private HerramientaMapper herramientaMapper;
    
    public EducacionDto toDto(Educacion educacion){
        
        EducacionDto educacionDto = new EducacionDto();
        educacionDto.setId_educacion(educacion.getId_educacion());
        educacionDto.setNombre_institucion(educacion.getNombre_institucion());
        educacionDto.setLogo_imagen(educacion.getLogo_imagen());
        educacionDto.setFecha_inicio(educacion.getFecha_inicio());
        educacionDto.setFecha_fin(educacion.getFecha_fin());
        educacionDto.setTitulo(educacion.getTitulo());
        educacionDto.setUrl_titulo(educacion.getUrl_titulo());
        
        educacionDto.setHerramientas(setHerramientaDto(educacion.getHerramientas()));
        
        return educacionDto;
    }
    
    public Set<HerramientaDto> setHerramientaDto(Set<Herramienta> setHerramienta){
        if (setHerramienta == null) {
            return new HashSet<>();
        }
        return setHerramienta.stream()
                .map(herramientaMapper::toDto)
                .collect(Collectors.toSet());
        }
    
}
