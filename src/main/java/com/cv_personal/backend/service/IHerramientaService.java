/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.HerramientaDto;
import com.cv_personal.backend.model.Herramienta;
import java.util.List;


public interface IHerramientaService {
    
    public HerramientaDto saveHerramienta(Herramienta herramienta);
    
    public List<HerramientaDto> getHerramienta();
    
    public HerramientaDto findHerramienta(Long id);
    
    public Herramienta updateHerramienta(Long id, HerramientaDto herramientaDto);
    
    public void deleteHerramienta(Long id);
    
    public HerramientaDto updateLogoImage(Long id, org.springframework.web.multipart.MultipartFile file);
    
}
