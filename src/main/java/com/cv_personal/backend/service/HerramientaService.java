/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.HerramientaDto;
import com.cv_personal.backend.mapper.HerramientaMapper;
import com.cv_personal.backend.model.Herramienta;
import com.cv_personal.backend.repository.IHerramientaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HerramientaService implements IHerramientaService {
    
    @Autowired
    private IHerramientaRepository herramientaRepo;
    
    @Autowired
    private HerramientaMapper herrMapper;
    
    
    @Override
    public HerramientaDto saveHerramienta(Herramienta herramienta) {
        Herramienta herramientaSave = herramientaRepo.save(herramienta);
        HerramientaDto herramientaDto = herrMapper.toDto(herramientaSave);
        return herramientaDto;
    }

    @Override
    public List<HerramientaDto> getHerramienta() {
        List<Herramienta> herramientas = herramientaRepo.findAll();
        List<HerramientaDto> listHerramientas = new ArrayList<>();
        
        for (Herramienta herramienta : herramientas){
            listHerramientas.add(herrMapper.toDto(herramienta));
        }
        return listHerramientas;
    }

    @Override
    public HerramientaDto findHerramienta(Long id) {
        Herramienta herramienta = herramientaRepo.findById(id).orElse(null);
        HerramientaDto herramientaDto = herrMapper.toDto(herramienta);
        return herramientaDto;
    }

    @Override
    public void deleteHerramienta(Long id) {
        herramientaRepo.deleteById(id);
    }

    @Override
    public Herramienta updateHerramienta(Long id) {
        Herramienta herramienta = herramientaRepo.findById(id).orElse(null);
        return herramienta;
    }
    
}
