/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.model.Proyecto;
import com.cv_personal.backend.repository.IProyectoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProyectoService implements IProyectoService{
    
    @Autowired
    private IProyectoRepository proRepo;

    @Override
    public void saveProyecto(Proyecto proyecto) {
        proRepo.save(proyecto);
    }

    @Override
    public List<Proyecto> getProyecto() {
        List<Proyecto> Proyecto = proRepo.findAll();
        return Proyecto;
    }

    @Override
    public Proyecto findProyecto(Long id) {
        Proyecto proyecto = proRepo.findById(id).orElse(null);
        return proyecto;
    }

    @Override
    public void deleteProyecto(Long id) {
        proRepo.deleteById(id);
    }
    
}
