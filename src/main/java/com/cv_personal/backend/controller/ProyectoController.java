/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.controller;

import com.cv_personal.backend.dto.ProyectoDto;
import com.cv_personal.backend.model.Proyecto;
import com.cv_personal.backend.service.IProyectoService;
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
@RequestMapping("/proyecto")
public class ProyectoController {
    
    @Autowired
    private IProyectoService proyService;
    
    @PostMapping("/save")
    public ResponseEntity<?> saveProyecto(@RequestBody Proyecto proyecto){
        try{
            ProyectoDto proyectoSave = proyService.saveProyecto(proyecto);
            return ResponseEntity.ok(proyectoSave);
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error","Registro duplicado"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error","Error interno en el servidor al intentar eliminar el registro"));
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getProyecto(){
        try{
            List<ProyectoDto> proyectos = proyService.getProyecto();
            return ResponseEntity.ok(proyectos);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el servidor");
        }
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findProyecto(@PathVariable Long id){
        try{
            ProyectoDto proyecto = proyService.findProyecto(id);
            return ResponseEntity.ok(proyecto);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor");
        }
    
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProyecto(@PathVariable Long id){
        try{
            proyService.deleteProyecto(id);
            return ResponseEntity.ok("Proyecto eliminado con exito");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el servidor al intentar eliminar el registro");
        }
    
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProyecto(@PathVariable Long id,
                                                @RequestBody Proyecto proyecto){
    
        try{
            Proyecto findProyecto = proyService.updateProyecto(id);
            if(findProyecto != null){
                findProyecto.setNombre(proyecto.getNombre());
                findProyecto.setDescripcion(proyecto.getDescripcion());
                findProyecto.setUrl(proyecto.getUrl());
                findProyecto.setInicio(proyecto.getInicio());
                findProyecto.setFin(proyecto.getFin());
                
                proyService.saveProyecto(findProyecto);
                
                return ResponseEntity.ok(proyService);
            }else{
                return ResponseEntity.badRequest().body("El registro con ese id no existe");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor el intentar modificar el registro");
        }
    }
    
}
