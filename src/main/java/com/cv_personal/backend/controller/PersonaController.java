package com.cv_personal.backend.controller;

import com.cv_personal.backend.dto.PersonaDto;
import com.cv_personal.backend.model.Persona;
import com.cv_personal.backend.service.IPersonaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
@RequestMapping("persona")
@CrossOrigin(origins = "*")
public class PersonaController {
    
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
    public ResponseEntity<?> updatePersona(@PathVariable Long id,
            @RequestBody Persona persona) {
        try {
            Persona findPersona = personaService.updatePersona(id);
            findPersona.setNombre(persona.getNombre());
            findPersona.setApellido(persona.getApellido());
            findPersona.setImagen_perfil(persona.getImagen_perfil());
            findPersona.setDescripcion_mi(persona.getDescripcion_mi());
            findPersona.setFecha_nacimiento(persona.getFecha_nacimiento());
            findPersona.setNum_celular(persona.getNum_celular());
            
            personaService.savePersona(findPersona);
            return ResponseEntity.ok("Datos personales actualizado con exito");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al intentar modificar el usuario");
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePersona(@PathVariable Long id){
        try{
            personaService.deletePersona(id);
            return ResponseEntity.ok("Usuario eliminado con exito");
        }catch (DataAccessException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al intentar eliminar el usuario");
        }
    
    }
    
}
