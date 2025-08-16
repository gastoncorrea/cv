/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.ProyectoDto;
import com.cv_personal.backend.mapper.ProyectoMapper;
import com.cv_personal.backend.model.Proyecto;
import com.cv_personal.backend.repository.IProyectoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProyectoService implements IProyectoService{
    
    @Autowired
    private IProyectoRepository proRepo;
    
    @Autowired
    private ProyectoMapper proyMap;

    @Override
    public ProyectoDto saveProyecto(Proyecto proyecto) {
        Proyecto proyectoSave = proRepo.save(proyecto);
        ProyectoDto proyectoDto = proyMap.toDto(proyectoSave);
        return proyectoDto;
    }

    @Override
    public List<ProyectoDto> getProyecto() {
        List<Proyecto> proyectos = proRepo.findAll();
        List<ProyectoDto> listProyectoDto = new ArrayList<>();
        
        for(Proyecto proyecto : proyectos){
            listProyectoDto.add(proyMap.toDto(proyecto));
        }
        return listProyectoDto;
    }

    @Override
    public ProyectoDto findProyecto(Long id) {
        Proyecto proyecto = proRepo.findById(id).orElse(null);
        ProyectoDto proyectoDto = proyMap.toDto(proyecto);
        return proyectoDto;
    }

    @Override
    public void deleteProyecto(Long id) {
        proRepo.deleteById(id);
    }

    @Override
    public Proyecto updateProyecto(Long id) {
        Proyecto proyecto = proRepo.findById(id).orElse(null);
        return proyecto;
    }
    
}
