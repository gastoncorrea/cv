package com.cv_personal.backend.controller;

import com.cv_personal.backend.dto.PersonaDto;
import com.cv_personal.backend.model.Persona;
import com.cv_personal.backend.service.IPersonaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("persona")
public class PersonaController {
    
    private static final Logger logger = LoggerFactory.getLogger(PersonaController.class);

    @Autowired
    private IPersonaService personaService;
    
    @PostMapping("/save")
    public ResponseEntity<?> savePersona(@RequestBody Persona persona) {
        try {
            PersonaDto personaSave = personaService.savePersona(persona);
            return ResponseEntity.ok(personaSave); // 200 OK
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar en la base de datos");
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getPersonas() {
        try {
            List<PersonaDto> listPersonas = personaService.getPersonas();
            return ResponseEntity.ok(listPersonas);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al guardar en la base de datos");
        }
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findIdPersona(@PathVariable Long id) {
        try {
            PersonaDto persona = personaService.findPersona(id);
            return ResponseEntity.ok(persona);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al guardar en la base de datos");
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePersona(@PathVariable Long id, @RequestBody PersonaDto personaDto) {
        try {
            PersonaDto updatedPersona = personaService.updatePersona(id, personaDto);
            return ResponseEntity.ok(updatedPersona);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<?> uploadProfileImage(@PathVariable Long id, @org.springframework.web.bind.annotation.RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            PersonaDto updatedPersona = personaService.updateProfileImage(id, file);
            return ResponseEntity.ok(updatedPersona);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePersona(@PathVariable Long id){
        logger.info("Received DELETE request for Persona with ID: {}", id); // Added log
        try{
            personaService.deletePersona(id);
            return ResponseEntity.ok("Persona eliminado con exito");
        }catch (DataAccessException e){ // Catch more specific exception first
            logger.error("DataAccessException when deleting Persona with ID {}: {}", id, e.getMessage()); // Added log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al intentar eliminar el usuario");
        }catch (RuntimeException e) { // Catch more general exception second
            logger.warn("RuntimeException when deleting Persona with ID {}: {}", id, e.getMessage()); // Added log
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    
    }
    
}
