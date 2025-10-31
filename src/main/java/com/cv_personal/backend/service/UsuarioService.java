/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.UsuarioDto;
import com.cv_personal.backend.mapper.UsuarioMapper;
import com.cv_personal.backend.model.Rol;
import com.cv_personal.backend.model.Usuario;
import com.cv_personal.backend.repository.IRolRepository;
import com.cv_personal.backend.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UsuarioService implements IUsuarioService {
    
    
    private final IUsuarioRepository usuarioRep;
    
    private final IRolRepository rolRepo; 
    
    private final UsuarioMapper usuarioMap;
    
    @Override
    public UsuarioDto saveUsuario(Usuario usuario) {
        log.info("Saving new user {} to database", usuario.getEmail());
        usuario = usuarioRep.save(usuario);
        UsuarioDto usuarioDto = usuarioMap.toDto(usuario);
        return usuarioDto;
    }

    @Override
    public List<UsuarioDto> getUsuario() {
        log.info("Fetching all users");
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

    @Override
    public Usuario getUsuario(String email) {
        log.info("Fetching user {}", email);
        return usuarioRep.findByEmail(email);
    }

    @Override
    public Rol saveRol(Rol rol) {
        log.info("Saving new {} rol to database", rol.getNombre());
        return rolRepo.save(rol);
    }

    @Override
    public void addRoleToUser(String email, String rolName) {
        log.info("Adding rol {} to User {}", rolName , email);
        Usuario usuario = usuarioRep.findByEmail(email);
        Rol rol = rolRepo.findByNombre(rolName);
        usuario.getRol().add(rol);
    }
    
}
