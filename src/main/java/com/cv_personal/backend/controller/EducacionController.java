/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.controller;

import com.cv_personal.backend.dto.EducacionDto;
import com.cv_personal.backend.model.Educacion;
import com.cv_personal.backend.dto.EducacionHerramientasDto;
import com.cv_personal.backend.service.IEducacionService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
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
            if (educacion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Educacion no encontrada con ID: " + id));
            }
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
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
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
                if(educacion.getNombre_institucion() != null && !educacion.getNombre_institucion().trim().isEmpty()){
                    findEducacion.setNombre_institucion(educacion.getNombre_institucion());
                }
                // Removed logo_imagen handling from here
                if(educacion.getFecha_inicio() != null){ // LocalDate can be null
                    findEducacion.setFecha_inicio(educacion.getFecha_inicio());
                }
                if(educacion.getFecha_fin() != null){ // LocalDate can be null
                    findEducacion.setFecha_fin(educacion.getFecha_fin());
                }
                if(educacion.getTitulo() != null && !educacion.getTitulo().trim().isEmpty()){
                    findEducacion.setTitulo(educacion.getTitulo());
                }
                if(educacion.getUrl_titulo() != null && !educacion.getUrl_titulo().trim().isEmpty()){
                    findEducacion.setUrl_titulo(educacion.getUrl_titulo());
                }
                
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

    @PostMapping("/{id}/logo")
    public ResponseEntity<?> uploadEducacionLogo(@PathVariable Long id,
                                                @RequestParam("file") MultipartFile file) {
        try {
            EducacionDto updatedEducacion = educService.updateLogoImage(id, file);
            return ResponseEntity.ok(updatedEducacion);
        } catch (RuntimeException e) { // Catch specific RuntimeException for not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) { // Catch general Exception for other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al subir la imagen del logo: " + e.getMessage()));
        }
    }

    @PostMapping("/herramientas")
    public ResponseEntity<?> addHerramientasToEducacion(@RequestBody EducacionHerramientasDto dto) {
        try {
            EducacionDto updatedEducacion = educService.addHerramientasToEducacion(dto);
            return ResponseEntity.ok(updatedEducacion);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al añadir herramientas a la educación: " + e.getMessage());
        }
    }

    @GetMapping("/persona/{personaId}")
    public ResponseEntity<?> getEducacionByPersonaId(@PathVariable Long personaId) {
        try {
            List<EducacionDto> educaciones = educService.getEducacionByPersonaId(personaId);
            if (educaciones.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(List.of()); // Return empty list with OK status
            }
            return ResponseEntity.ok(educaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener educaciones por ID de Persona: " + e.getMessage());
        }
    }

    @DeleteMapping("/{educacionId}/herramientas/{herramientaId}")
    public ResponseEntity<?> removeHerramientaFromEducacion(@PathVariable Long educacionId, @PathVariable Long herramientaId) {
        try {
            EducacionDto updatedEducacion = educService.removeHerramientaFromEducacion(educacionId, herramientaId);
            return ResponseEntity.ok(updatedEducacion);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al eliminar la herramienta de la educación: " + e.getMessage()));
        }
    }
}

