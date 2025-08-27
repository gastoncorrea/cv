/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.controller;

import com.cv_personal.backend.dto.EducacionDto;
import com.cv_personal.backend.model.Educacion;
import com.cv_personal.backend.service.IEducacionService;
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
@RequestMapping("/educacion")
public class EducacionController {
    
    @Autowired
    private IEducacionService educService;
    
    @PostMapping("/save")
    public ResponseEntity<?> saveEducacion(@RequestBody Educacion educacion){
        try{
            EducacionDto educacionSave = educService.saveEducacion(educacion);
            return ResponseEntity.ok(educacionSave);
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
    
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findEducacion(@PathVariable Long id){
        try{
            EducacionDto educacion = educService.findEducacion(id);
            return ResponseEntity.ok(educacion);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor");
        }
    
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEducacion(@PathVariable Long id){
        try{
            educService.deleteEducacion(id);
            return ResponseEntity.ok("Educacion eliminada con exito");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el servidor al intentar eliminar el registro");
        }
    
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEducacion(@PathVariable Long id,
                                                @RequestBody Educacion educacion){
    
        try{
            Educacion findEducacion = educService.updateEducacion(id);
            if(findEducacion != null){
                findEducacion.setNombre_institucion(educacion.getNombre_institucion());
                findEducacion.setLogo_imagen(educacion.getLogo_imagen());
                findEducacion.setFecha_inicio(educacion.getFecha_inicio());
                findEducacion.setFecha_fin(educacion.getFecha_fin());
                findEducacion.setTitulo(educacion.getTitulo());
                findEducacion.setUrl_titulo(educacion.getUrl_titulo());
                
                EducacionDto educacionSave = educService.saveEducacion(findEducacion);
                
                return ResponseEntity.ok(educacionSave);
            }else{
                return ResponseEntity.badRequest().body("El registro con ese id no existe");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor el intentar modificar el registro");
        }
    }
    
    
}
