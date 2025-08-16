package com.cv_personal.backend.controller;

import com.cv_personal.backend.model.Persona;
import com.cv_personal.backend.service.IPersonaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
            personaService.savePersona(persona);
            return ResponseEntity.ok("Exito en guardar el registro"); // 200 OK
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar en la base de datos");
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getPersonas() {
        try {
            List<Persona> listPersonas = personaService.getPersonas();
            return ResponseEntity.ok(listPersonas);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al guardar en la base de datos");
        }
    }
    
}


