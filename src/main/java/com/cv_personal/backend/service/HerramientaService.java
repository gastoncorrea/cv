/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.model.Herramienta;
import com.cv_personal.backend.repository.IHerramientaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HerramientaService implements IHerramientaService {
    
    @Autowired
    private IHerramientaRepository herramientaRepo;
    
    
    @Override
    public void saveHerramienta(Herramienta herramienta) {
        herramientaRepo.save(herramienta);
    }

    @Override
    public List<Herramienta> getHerramienta() {
        List<Herramienta> herramientas = herramientaRepo.findAll();
        return herramientas;
    }

    @Override
    public Herramienta findHerramienta(Long id) {
        Herramienta herramienta = herramientaRepo.findById(id).orElse(null);
        return herramienta;
    }

    @Override
    public void deleteHerramienta(Long id) {
        herramientaRepo.deleteById(id);
    }
    
}
