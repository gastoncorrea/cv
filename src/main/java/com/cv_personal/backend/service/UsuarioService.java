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
import com.cv_personal.backend.security.CustomUserDetails; // Import CustomUserDetails
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UsuarioService implements IUsuarioService, UserDetailsService {
    
    private final IUsuarioRepository usuarioRep;
    private final IRolRepository rolRepo; 
    private final UsuarioMapper usuarioMap;
    private final PasswordEncoder passwordEncoder;
    
     @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRep.findByEmail(email);
        if(usuario == null){
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        }else{
            log.info("User found in database: {}",email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        usuario.getRol().forEach(rol -> {
            authorities.add(new SimpleGrantedAuthority(rol.getNombre()));
        });
        
        // Retrieve personId from the associated Persona
        Long personId = null;
        if (usuario.getPersona() != null) {
            personId = usuario.getPersona().getId_persona();
        }
        
        return new CustomUserDetails(usuario.getEmail(), usuario.getPassword(), authorities, personId);
    }
    
    @Override
    public UsuarioDto saveUsuario(Usuario usuario) {
        log.info("Saving new user {} to database", usuario.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
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
    public UsuarioDto updateUsuario(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRep.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con id: " + id));
        
        if (usuarioDto.getEmail() != null && !usuarioDto.getEmail().trim().isEmpty()) {
            usuario.setEmail(usuarioDto.getEmail());
        }
        if (usuarioDto.getNombre() != null && !usuarioDto.getNombre().trim().isEmpty()) {
            usuario.setNombre(usuarioDto.getNombre());
        }
        // Assuming Rol in UsuarioDto is a List<Rol> or similar that can be null
        // If it's a DTO for Rol, further mapping logic might be needed here
        if (usuarioDto.getRol() != null && !usuarioDto.getRol().isEmpty()) { // Add check for empty list as well
            usuario.setRol(usuarioDto.getRol());
        }
        
        if (usuarioDto.getPassword() != null && !usuarioDto.getPassword().trim().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        }
        
        Usuario updatedUsuario = usuarioRep.save(usuario);
        return usuarioMap.toDto(updatedUsuario);
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
