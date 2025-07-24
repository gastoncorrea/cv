/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.model.Usuario;
import com.cv_personal.backend.repository.IUsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService {
    
    @Autowired
    private IUsuarioRepository usuarioRep;
    
    @Override
    public void saveUsuario(Usuario usuario) {
        usuarioRep.save(usuario);
    }

    @Override
    public List<Usuario> getUsuario() {
        List<Usuario> listUsuario = usuarioRep.findAll();
        return listUsuario;
    }

    @Override
    public Usuario findUsuario(Long id) {
        Usuario usuario = usuarioRep.findById(id).orElse(null);
        return usuario;
    }

    @Override
    public void deleteUsuario(Long id) {
        usuarioRep.deleteById(id);
    }
    
}
