/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.UsuarioDto;
import com.cv_personal.backend.mapper.UsuarioMapper;
import com.cv_personal.backend.model.Usuario;
import com.cv_personal.backend.repository.IUsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService {
    
    @Autowired
    private IUsuarioRepository usuarioRep;
    
    @Autowired
    private UsuarioMapper usuarioMap;
    
    @Override
    public UsuarioDto saveUsuario(Usuario usuario) {
        usuario = usuarioRep.save(usuario);
        UsuarioDto usuarioDto = usuarioMap.toDto(usuario);
        return usuarioDto;
    }

    @Override
    public List<UsuarioDto> getUsuario() {
        List<Usuario> listUsuario = usuarioRep.findAll();
        List<UsuarioDto> listUsuarioDto = new ArrayList<>();
        
        for(Usuario usuario : listUsuario){
            listUsuarioDto.add(usuarioMap.toDto(usuario));
        }
        
        return listUsuarioDto;
    }

    @Override
    public UsuarioDto findUsuario(Long id) {
        Usuario usuario = usuarioRep.findById(id).orElse(null);
        UsuarioDto usuarioDto = usuarioMap.toDto(usuario);
        return usuarioDto;
    }

    @Override
    public void deleteUsuario(Long id) {
        usuarioRep.deleteById(id);
    }

    @Override
    public Usuario updateUsuario(Long id) {
        Usuario usuario = usuarioRep.findById(id).orElse(null);
        return usuario;
    }
    
}
