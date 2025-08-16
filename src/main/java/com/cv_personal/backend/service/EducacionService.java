/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.EducacionDto;
import com.cv_personal.backend.mapper.EducacionMapper;
import com.cv_personal.backend.model.Educacion;
import com.cv_personal.backend.repository.IEducacionRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducacionService implements IEducacionService{
    
    @Autowired
    private IEducacionRepository educRepository;
    
    @Autowired
    private EducacionMapper educMap;
    
    @Override
    public EducacionDto saveEducacion(Educacion educacion) {
        Educacion educacionSave = educRepository.save(educacion);
        EducacionDto educacionDto = educMap.toDto(educacionSave);
        return educacionDto;
    }

    @Override
    public List<EducacionDto> getEducacion() {
        List<Educacion> educaciones = educRepository.findAll();
        List<EducacionDto> listEducacionDto = new ArrayList<>();
        
        for(Educacion educacion : educaciones){
            listEducacionDto.add(educMap.toDto(educacion));
        }
        
        return listEducacionDto;
    }

    @Override
    public EducacionDto findEducacion(Long id) {
        Educacion educacion = educRepository.findById(id).orElse(null);
        EducacionDto educacionDto = educMap.toDto(educacion);
        return educacionDto;
    }

    @Override
    public void deleteEducacion(Long id) {
        educRepository.deleteById(id);
    }

    @Override
    public Educacion updateEducacion(Long id) {
        Educacion educacion = educRepository.findById(id).orElse(null);
        return educacion;
    }
    
}
