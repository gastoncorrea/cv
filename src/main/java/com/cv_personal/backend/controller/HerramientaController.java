/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.controller;

import com.cv_personal.backend.dto.HerramientaDto;
import com.cv_personal.backend.model.Herramienta;
import com.cv_personal.backend.service.IHerramientaService;
import java.io.IOException; // Import IOException
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
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
            if (herramienta == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Herramienta no encontrada con ID: " + id));
            }
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
            return ResponseEntity.ok(Map.of("message", "Herramienta eliminada con exito"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error","Error interno en el servidor al intentar eliminar el registro"));
        }
    
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHerramienta(@PathVariable Long id,
                                                @RequestBody HerramientaDto herramientaDto){
        try{
            Herramienta updatedHerramienta = herrService.updateHerramienta(id, herramientaDto);
            return ResponseEntity.ok(herrService.findHerramienta(updatedHerramienta.getId_herramienta()));
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error","Error interno en el servidor al intentar modificar el registro: " + e.getMessage()));
        }
    }
    
    @PostMapping("/{id}/logo")
    public ResponseEntity<?> uploadHerramientaLogo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            HerramientaDto updatedHerramienta = herrService.updateLogoImage(id, file);
            return ResponseEntity.ok(updatedHerramienta);
        } catch (RuntimeException e) { // Catch specific RuntimeException for not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) { // Catch general Exception for other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al cargar el logo: " + e.getMessage()));
        }
    }
}

