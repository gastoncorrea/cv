/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.model.Educacion;
import java.util.List;


public interface IEducacionService {
    
    public void saveEducacion(Educacion educacion);
    
    public List<Educacion> getEducacion();
    
    public Educacion findEducacion(Long id);
    
    public void deleteEducacion(Long id);
}
