/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.model.Herramienta;
import java.util.List;


public interface IHerramientaService {
    
    public void saveHerramienta(Herramienta herramienta);
    
    public List<Herramienta> getHerramienta();
    
    public Herramienta findHerramienta(Long id);
    
    public void deleteHerramienta(Long id);
    
}
