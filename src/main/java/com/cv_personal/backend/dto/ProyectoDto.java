/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.dto;

import com.cv_personal.backend.model.Herramienta;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProyectoDto {
    
    private Long id_proyecto;
    private String nombre;
    private String descripcion;
    private String url;
    private LocalDate inicio;
    private LocalDate fin;
    
    private List<Herramienta> herramientas;
    
}
