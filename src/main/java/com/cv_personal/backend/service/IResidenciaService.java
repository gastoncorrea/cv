/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.model.Residencia;
import java.util.List;


public interface IResidenciaService {
    
    public void saveResidencia(Residencia residencia);
    
    public List<Residencia> getResidencia();
    
    public Residencia findResidencia(Long id);
    
    public void deleteResidencia(Long id);
    
}
