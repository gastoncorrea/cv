/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.dto;

import com.cv_personal.backend.model.Herramienta;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EducacionDto {
    
    private Long id_educacion;
    private String nombre_institucion;
    private String logo_imagen;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private String titulo;
    private String url_titulo;
    
    private Set<HerramientaDto> herramientas;
    
}
