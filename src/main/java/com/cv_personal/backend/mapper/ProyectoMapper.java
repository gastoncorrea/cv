/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.mapper;

import com.cv_personal.backend.dto.HerramientaDto;
import com.cv_personal.backend.dto.ProyectoDto;
import com.cv_personal.backend.model.Herramienta;
import com.cv_personal.backend.model.Proyecto;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProyectoMapper {
    
    @Autowired
    private HerramientaMapper herramientaMapper;

    public ProyectoDto toDto(Proyecto proyecto) {
        
        ProyectoDto proyectoDto = new ProyectoDto();
        
        proyectoDto.setId_proyecto(proyecto.getId_proyecto());
        proyectoDto.setNombre(proyecto.getNombre());
        proyectoDto.setDescripcion(proyecto.getDescripcion());
        proyectoDto.setInicio(proyecto.getInicio());
        proyectoDto.setFin(proyecto.getFin());
        proyectoDto.setUrl(proyecto.getUrl());
        proyectoDto.setLogo_proyecto(proyecto.getLogo_proyecto());
        proyectoDto.setHerramientas(setHerramientaDto(proyecto.getHerramientas()));
        
        return proyectoDto;
        
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
