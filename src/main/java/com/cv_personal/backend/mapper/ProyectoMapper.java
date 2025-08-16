/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.mapper;

import com.cv_personal.backend.dto.ProyectoDto;
import com.cv_personal.backend.model.Proyecto;

public class ProyectoMapper {

    public ProyectoDto toDto(Proyecto proyecto) {
        
        ProyectoDto proyectoDto = new ProyectoDto();
        
        proyectoDto.setId_proyecto(proyecto.getId_proyecto());
        proyectoDto.setNombre(proyecto.getNombre());
        proyectoDto.setDescripcion(proyecto.getDescripcion());
        proyectoDto.setInicio(proyecto.getInicio());
        proyectoDto.setFin(proyecto.getFin());
        proyectoDto.setUrl(proyecto.getUrl());
        
        return proyectoDto;
        
    }
}
