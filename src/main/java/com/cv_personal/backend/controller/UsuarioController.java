/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.controller;

import com.cv_personal.backend.model.Usuario;
import com.cv_personal.backend.service.IUsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioService.saveUsuario(usuario);
            return ResponseEntity.ok("Exito al guardar el registro");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al guardar en la base de datos");
        }

    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getUsuario() {
        try {
            List<Usuario> listUsuario = usuarioService.getUsuario();
            return ResponseEntity.ok(listUsuario);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al guardar en la base de datos");
        }
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findIdUsuario(@PathVariable Long id){
        try{
            Usuario usuario = usuarioService.findUsuario(id);
            return ResponseEntity.ok(usuario);
        }catch (DataAccessException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al guardar en la base de datos");
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUsario(@PathVariable Long id,
                                            @RequestBody Usuario usuario){
        try{
            Usuario findUsuario = usuarioService.findUsuario(id);
            findUsuario.setEmail(usuario.getEmail());
            findUsuario.setPassword(usuario.getPassword());
            findUsuario.setRol(usuario.getRol());
            usuarioService.saveUsuario(findUsuario);
            return ResponseEntity.ok("Usuario actualizado con exito");
        }catch(DataAccessException e){
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
}
