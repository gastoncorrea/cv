/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.ResidenciaDto;
import com.cv_personal.backend.mapper.ResidenciaMapper;
import com.cv_personal.backend.model.Residencia;
import com.cv_personal.backend.repository.IResidenciaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidenciaService implements IResidenciaService {
    
    @Autowired
    private IResidenciaRepository residenciaRepository;
    
    @Autowired
    private ResidenciaMapper resMapper;
    
    @Override
    public ResidenciaDto saveResidencia(Residencia residencia) {
        Residencia residenciaSave = residenciaRepository.save(residencia);
        ResidenciaDto residenciaDto = resMapper.toDto(residenciaSave);
        return residenciaDto;
    }

    @Override
    public List<ResidenciaDto> getResidencia() {
        List<Residencia> residencias = residenciaRepository.findAll();
        List<ResidenciaDto> residenciasDto = new ArrayList<>();
        
        for(Residencia residencia : residencias){
            residenciasDto.add(resMapper.toDto(residencia));
        }
        
        return residenciasDto;
    }

    @Override
    public ResidenciaDto findResidencia(Long id) {
        Residencia residencia = residenciaRepository.findById(id).orElse(null);
        ResidenciaDto findResidencia = resMapper.toDto(residencia);
        
        return findResidencia;
    }

    @Override
    public void deleteResidencia(Long id) {
        residenciaRepository.deleteById(id);    
    }

    @Override
    public Residencia updateResidencia(Long id) {
        Residencia residencia = residenciaRepository.findById(id).orElse(null);
        return residencia;
    }
    
}
