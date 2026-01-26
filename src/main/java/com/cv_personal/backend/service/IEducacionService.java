/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.EducacionDto;
import com.cv_personal.backend.model.Educacion;
import com.cv_personal.backend.dto.EducacionHerramientasDto; // Import DTO
import java.util.List;


public interface IEducacionService {
    
    public EducacionDto saveEducacion(Educacion educacion);
    
    public List<EducacionDto> getEducacion();
    
    public EducacionDto findEducacion(Long id);
    
    public Educacion updateEducacion(Long id);
    
    public void deleteEducacion(Long id);
    
    public EducacionDto addHerramientasToEducacion(EducacionHerramientasDto dto);
}

