/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.controller;

import com.cv_personal.backend.model.Contacto;
import com.cv_personal.backend.service.IContactoService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/contacto")
public class ContactoController {
    
    @Autowired
    private IContactoService contacService;
    
    @PostMapping("/save")
    public ResponseEntity<?> saveContacto(@RequestBody Contacto contacto){
        try{
            contacService.saveContacto(contacto);
            return ResponseEntity.ok("Contacto creado con exito");
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error","Registro duplicado"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error","Error interno en el servidor al intentar crear el registro"));
        }
    }
    
    
}
