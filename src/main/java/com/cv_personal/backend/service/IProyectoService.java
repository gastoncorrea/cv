/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.ProyectoDto;
import com.cv_personal.backend.model.Proyecto;
import com.cv_personal.backend.dto.ProyectoHerramientasDto; // Import DTO
import java.util.List;


public interface IProyectoService {
    
    public ProyectoDto saveProyecto(Proyecto proyecto);
    
    public List<ProyectoDto> getProyecto();
    
    public ProyectoDto findProyecto(Long id);
    
    public Proyecto updateProyecto(Long id);
    
    public void deleteProyecto(Long id);
    
    public ProyectoDto addHerramientasToProyecto(ProyectoHerramientasDto dto);
    
    public List<ProyectoDto> getProyectoByPersonaId(Long personaId);
}
