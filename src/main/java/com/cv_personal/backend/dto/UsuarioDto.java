/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.dto;

import com.cv_personal.backend.model.Rol;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioDto {
    
    private Long id_usuario;
    private String email;
    private String nombre;
    private String password;
    private Collection<Rol> rol = new ArrayList<>();
    
}
