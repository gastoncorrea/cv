/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.controller;

import com.cv_personal.backend.dto.ProyectoDto;
import com.cv_personal.backend.model.Proyecto;
import com.cv_personal.backend.dto.ProyectoHerramientasDto;
import com.cv_personal.backend.service.IProyectoService;
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


@RestController
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
                    .body(Map.of("error","Error interno en el servidor al intentar guardar el registro"));
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
            if (proyecto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado con ID: " + id);
            }
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
        }catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el servidor al intentar eliminar el registro");
        }
    
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProyecto(@PathVariable Long id,
                                                @RequestBody ProyectoDto proyectoDto){
    
        try{
            ProyectoDto updatedProyecto = proyService.updateProyecto(id, proyectoDto);
            return ResponseEntity.ok(updatedProyecto);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor al intentar modificar el registro");
        }
    }

    @PostMapping("/{id}/logo")
    public ResponseEntity<?> updateProyectoLogo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            ProyectoDto updatedProyecto = proyService.updateLogoImage(id, file);
            return ResponseEntity.ok(updatedProyecto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el logo del proyecto: " + e.getMessage());
        }
    }

    @PostMapping("/herramientas")
    public ResponseEntity<?> addHerramientasToProyecto(@RequestBody ProyectoHerramientasDto dto) {
        try {
            ProyectoDto updatedProyecto = proyService.addHerramientasToProyecto(dto);
            return ResponseEntity.ok(updatedProyecto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al añadir herramientas al proyecto: " + e.getMessage());
        }
    }

    @GetMapping("/persona/{personaId}")
    public ResponseEntity<?> getProyectoByPersonaId(@PathVariable Long personaId) {
        try {
            List<ProyectoDto> proyectos = proyService.getProyectoByPersonaId(personaId);
            if (proyectos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(List.of()); // Return empty list with OK status
            }
            return ResponseEntity.ok(proyectos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener proyectos por ID de Persona: " + e.getMessage());
        }
    }

    @DeleteMapping("/{proyectoId}/herramientas/{herramientaId}")
    public ResponseEntity<?> removeHerramientaFromProyecto(@PathVariable Long proyectoId, @PathVariable Long herramientaId) {
        try {
            ProyectoDto updatedProyecto = proyService.removeHerramientaFromProyecto(proyectoId, herramientaId);
            return ResponseEntity.ok(updatedProyecto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al eliminar la herramienta del proyecto: " + e.getMessage()));
        }
    }
}
