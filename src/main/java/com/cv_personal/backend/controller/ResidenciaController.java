/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.controller;

import com.cv_personal.backend.dto.ResidenciaDto;
import com.cv_personal.backend.model.Residencia;
import com.cv_personal.backend.service.IResidenciaService;
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
@RequestMapping("/residencia")
public class ResidenciaController {
    
    @Autowired
    private IResidenciaService resiService;
    
    @PostMapping("/save")
    public ResponseEntity<?> saveResidencia(@RequestBody Residencia residencia){
        try{
            ResidenciaDto residenciaSave = resiService.saveResidencia(residencia);
            return ResponseEntity.ok(residenciaSave);
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error","Registro duplicado"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error","Error interno en el servidor al intentar crear el registro"));
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getResidencia(){
        try{
            List<ResidenciaDto> residencia = resiService.getResidencia();
            return ResponseEntity.ok(residencia);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el servidor ");
        }
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findResidencia(@PathVariable Long id){
        try{
            ResidenciaDto residencia = resiService.findResidencia(id);
            return ResponseEntity.ok(residencia);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor");
        }
    
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteResidencia(@PathVariable Long id){
        try{
            resiService.deleteResidencia(id);
            return ResponseEntity.ok("Residencia eliminada con exito");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el servidor al intentar eliminar el registro");
        }
    
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateResidencia(@PathVariable Long id,
                                                @RequestBody Residencia residencia){
    
        try{
            Residencia findResidencia = resiService.updateResidencia(id);
            if(findResidencia != null){
                findResidencia.setLocalidad(residencia.getLocalidad());
                findResidencia.setProvincia(residencia.getProvincia());
                findResidencia.setPais(residencia.getPais());
                findResidencia.setNacionalidad(residencia.getNacionalidad());
                
                
                resiService.saveResidencia(findResidencia);
                
                return ResponseEntity.ok(findResidencia);
            }else{
                return ResponseEntity.badRequest().body("El registro con ese id no existe");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor el intentar modificar el registro");
        }
    }
}
