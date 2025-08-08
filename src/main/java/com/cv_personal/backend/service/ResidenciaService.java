/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.model.Residencia;
import com.cv_personal.backend.repository.IResidenciaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidenciaService implements IResidenciaService {
    
    @Autowired
    private IResidenciaRepository residenciaRepository;
    @Override
    public void saveResidencia(Residencia residencia) {
        residenciaRepository.save(residencia);
    }

    @Override
    public List<Residencia> getResidencia() {
        List<Residencia> residencia = residenciaRepository.findAll();
        return residencia;
    }

    @Override
    public Residencia findResidencia(Long id) {
        Residencia residencia = residenciaRepository.findById(id).orElse(null);
        return residencia;
    }

    @Override
    public void deleteResidencia(Long id) {
        residenciaRepository.deleteById(id);    
    }
    
}
