/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.controller;

import com.cv_personal.backend.dto.HerramientaDto;
import com.cv_personal.backend.model.Herramienta;
import com.cv_personal.backend.service.IHerramientaService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
@CrossOrigin("*")
@RequestMapping("/herramienta")
public class HerramientaController {
    
    @Autowired
    private IHerramientaService herrService;
    
    @PostMapping("/save")
    public ResponseEntity<?> saveHerramienta(@RequestBody Herramienta herramienta){
        try{
            HerramientaDto herramientaSave = herrService.saveHerramienta(herramienta);
            return ResponseEntity.ok(herramientaSave);
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error","Registro duplicado"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error","Error interno en el servidor al intentar crear el registro"));
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getHerramienta(){
        try{
            List<HerramientaDto> herramienta = herrService.getHerramienta();
            return ResponseEntity.ok(herramienta);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el servidor ");
        }
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findHerramienta(@PathVariable Long id){
        try{
            HerramientaDto herramienta = herrService.findHerramienta(id);
            return ResponseEntity.ok(herramienta);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor");
        }
    
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHerramienta(@PathVariable Long id){
        try{
            herrService.deleteHerramienta(id);
            return ResponseEntity.ok("Herramienta eliminada con exito");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el servidor al intentar eliminar el registro");
        }
    
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHerramienta(@PathVariable Long id,
                                                @RequestBody Herramienta herramienta){
    
        try{
            Herramienta findHerramienta = herrService.updateHerramienta(id);
            if(findHerramienta != null){
                findHerramienta.setNombre(herramienta.getNombre());
                findHerramienta.setVersion(herramienta.getVersion());                
                
                herrService.saveHerramienta(findHerramienta);
                
                return ResponseEntity.ok(findHerramienta);
            }else{
                return ResponseEntity.badRequest().body("El registro con ese id no existe");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor el intentar modificar el registro");
        }
    }
    
}
