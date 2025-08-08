/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.model.Proyecto;
import java.util.List;


public interface IProyectoService {
    
    public void saveProyecto(Proyecto proyecto);
    
    public List<Proyecto> getProyecto();
    
    public Proyecto findProyecto(Long id);
    
    public void deleteProyecto(Long id);
}
