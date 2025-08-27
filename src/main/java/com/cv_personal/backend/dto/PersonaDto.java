/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.dto;

import com.cv_personal.backend.model.Contacto;
import com.cv_personal.backend.model.Residencia;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonaDto {
    
    private Long id_persona;
    private String nombre;
    private String apellido;
    private byte[] imagen_perfil;
    private String descripcion_mi;
    private LocalDate fecha_nacimiento;
    private String num_celular;
    
    private List<ResidenciaDto> residencias;
    
    private List<ContactoDto> contactos;
}
