/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.model.Educacion;
import com.cv_personal.backend.repository.IEducacionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducacionService implements IEducacionService{
    
    @Autowired
    private IEducacionRepository educRepository;
    
    @Override
    public void saveEducacion(Educacion educacion) {
        educRepository.save(educacion);
    }

    @Override
    public List<Educacion> getEducacion() {
        List<Educacion> educaciones = educRepository.findAll();
        return educaciones;
    }

    @Override
    public Educacion findEducacion(Long id) {
        Educacion educacion = educRepository.findById(id).orElse(null);
        return educacion;
    }

    @Override
    public void deleteEducacion(Long id) {
        educRepository.deleteById(id);
    }
    
}
