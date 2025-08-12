/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonaDto {
    
    private Long id_persona;
    private String nombre;
    private String apellido;
    private byte[] imagen_perfil;
    private String descripcion_mi;
    private String url_linkedin;
    private LocalDate fecha_nacimiento;
    private String num_celular;
}
