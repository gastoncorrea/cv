/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.controller;

import com.cv_personal.backend.dto.UsuarioDto;
import com.cv_personal.backend.model.Rol;
import com.cv_personal.backend.model.Usuario;
import com.cv_personal.backend.service.IUsuarioService;
import java.net.URI;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final IUsuarioService usuarioService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUsuario(@RequestBody Usuario usuario) {
        try {
            URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/usuario/save").build().toUri();
            return ResponseEntity.created(uri).body(usuarioService.saveUsuario(usuario));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al guardar en la base de datos");
        }

    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getUsuario() {
        try {
            List<UsuarioDto> listUsuario = usuarioService.getUsuario();
            return ResponseEntity.ok().body(listUsuario);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al guardar en la base de datos");
        }
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findIdUsuario(@PathVariable Long id){
        try{
            UsuarioDto usuario = usuarioService.findUsuario(id);
            return ResponseEntity.ok(usuario);
        }catch (DataAccessException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al guardar en la base de datos");
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUsario(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto){
        try{
            UsuarioDto updatedUsuario = usuarioService.updateUsuario(id, usuarioDto);
            return ResponseEntity.ok(updatedUsuario);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(DataAccessException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al intentar modificar el usuario");
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id){
        try{
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok("Usuario eliminado con exito");
        }catch (DataAccessException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al intentar eliminar el usuario");
        }
    
    }
    
    @PostMapping("/rol/save")
    public ResponseEntity<Rol> saveRol(@RequestBody Rol rol){
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/usuario/rol/save").build().toUri();
            return ResponseEntity.created(uri).body(usuarioService.saveRol(rol));
    }
    
    @PostMapping("/rol/addtouser")
    public ResponseEntity<?> saveRolToUser(@RequestBody RolToUserForm form){
        usuarioService.addRoleToUser(form.getEmail(), form.getRolName());
            return ResponseEntity.ok().build();
    }
    @Data
    public class RolToUserForm{
        private String email;
        private String rolName;
    }
}

    
