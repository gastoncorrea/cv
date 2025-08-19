/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.mapper;

import com.cv_personal.backend.dto.HerramientaDto;
import com.cv_personal.backend.model.Herramienta;
import org.springframework.stereotype.Component;

@Component
public class HerramientaMapper {
    
    public HerramientaDto toDto(Herramienta herramienta){
            
            HerramientaDto herramientaDto = new HerramientaDto();
            
            herramientaDto.setId_herramienta(herramienta.getId_herramienta());
            herramientaDto.setNombre(herramienta.getNombre());
            herramientaDto.setVersion(herramienta.getVersion());
            
            return herramientaDto;
    }
}
