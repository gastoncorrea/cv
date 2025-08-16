/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.UsuarioDto;
import com.cv_personal.backend.model.Usuario;
import java.util.List;


public interface IUsuarioService {
    
    public UsuarioDto saveUsuario(Usuario usuario);
    
    public List<UsuarioDto> getUsuario();
    
    public UsuarioDto findUsuario(Long id);
    
    public Usuario updateUsuario(Long id);
    
    public void deleteUsuario(Long id);
    
}
