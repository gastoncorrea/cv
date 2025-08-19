/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.mapper;

import com.cv_personal.backend.dto.ResidenciaDto;
import com.cv_personal.backend.model.Residencia;
import org.springframework.stereotype.Component;

@Component
public class ResidenciaMapper {
    
    public ResidenciaDto toDto(Residencia residencia) {
        ResidenciaDto residenciaDto = new ResidenciaDto();
        
        residenciaDto.setId_residencia(residencia.getId_residencia());
        residenciaDto.setLocalidad(residencia.getLocalidad());
        residenciaDto.setProvincia(residencia.getProvincia());
        residenciaDto.setPais(residencia.getPais());
        residenciaDto.setNacionalidad(residencia.getNacionalidad());
        
        return residenciaDto;
        
    }
}
