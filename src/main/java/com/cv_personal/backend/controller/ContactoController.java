/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/contacto")
public class ContactoController {
    
    @Autowired
    private IContactoService educService;
    
    @PostMapping("/save")
    public ResponseEntity<?> saveEducacion(@RequestBody Educacion educacion){
        try{
            educService.saveEducacion(educacion);
            return ResponseEntity.ok("Educacion creada con exito");
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error","Registro duplicado"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error","Error interno en el servidor al intentar eliminar el registro"));
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getEducacion(){
        try{
            List<EducacionDto> educaciones = educService.getEducacion();
            return ResponseEntity.ok(educaciones);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el servidor ");
        }
    }
    
}
