/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.mapper;

import com.cv_personal.backend.dto.UsuarioDto;
import com.cv_personal.backend.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    
    public UsuarioDto toDto(Usuario usuario){
        
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId_usuario(usuario.getId_usuario());
        usuarioDto.setEmail(usuario.getEmail());
        
        return usuarioDto;
    }
}
