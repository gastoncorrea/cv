/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.controller;

import com.cv_personal.backend.dto.ContactoDto;
import com.cv_personal.backend.model.Contacto;
import com.cv_personal.backend.service.IContactoService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@RestController

@RequestMapping("/contacto")

public class ContactoController {

    

    @Autowired

    private IContactoService contacService;

    

    @PostMapping("/save")

    public ResponseEntity<?> saveContacto(@RequestBody Contacto contacto){

        try{

            ContactoDto contactoSave = contacService.saveContacto(contacto);

            return ResponseEntity.ok(contactoSave);

        }catch(DataIntegrityViolationException e){

            return ResponseEntity.status(HttpStatus.CONFLICT)

                    .body(Map.of("error","Registro duplicado"));

        }catch(Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

                    .body(Map.of("error","Error interno en el servidor al intentar crear el registro"));

        }

    }

    

    @GetMapping("/all")

    public ResponseEntity<?> getContacto(){

        try{

            List<ContactoDto> contactos = contacService.getContacto();

            return ResponseEntity.ok(contactos);

        }catch(Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

                    .body("Error interno en el servidor ");

        }

    }

    

    @GetMapping("/find/{id}")

    public ResponseEntity<?> findContacto(@PathVariable Long id){

        try{

            ContactoDto contacto = contacService.findContacto(id);
            if (contacto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Contacto no encontrado con ID: " + id));
            }
            return ResponseEntity.ok(contacto);

        }catch(Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

                    .body("Error en el servidor");

        }

    

    }

    

    @DeleteMapping("/delete/{id}")

    public ResponseEntity<?> deleteContacto(@PathVariable Long id){

        try{

            contacService.deleteContacto(id);

            return ResponseEntity.ok("Contacto eliminado con exito");

        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }catch(Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

                    .body("Error interno en el servidor al intentar eliminar el registro");

        }

    

    }

    

    @PutMapping("/update/{id}")

    public ResponseEntity<?> updateContacto(@PathVariable Long id,

                                                @RequestBody Contacto contacto){

    

        try{

            Contacto findContacto = contacService.updateContacto(id);

            if(findContacto != null){

                if(contacto.getNombre() != null && !contacto.getNombre().trim().isEmpty()){

                    findContacto.setNombre(contacto.getNombre());

                }

                if(contacto.getUrl_contacto() != null && !contacto.getUrl_contacto().trim().isEmpty()){

                    findContacto.setUrl_contacto(contacto.getUrl_contacto());

                }

                

                ContactoDto contactoSave = contacService.saveContacto(findContacto);

                

                return ResponseEntity.ok(contactoSave);

            }else{

                return ResponseEntity.badRequest().body("El registro con ese id no existe");

            }

        }catch(Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

                    .body("Error en el servidor el intentar modificar el registro");

        }

    }



    @PostMapping("/{id}/logo")

    public ResponseEntity<?> uploadContactLogo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {

        try {

            ContactoDto updatedContacto = contacService.updateLogoImage(id, file);

            return ResponseEntity.ok(updatedContacto);

        } catch (RuntimeException e) { // Catch specific RuntimeException for not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) { // Catch general Exception for other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al subir el logo: " + e.getMessage()));
        }

    }

    @GetMapping("/persona/{personaId}")
    public ResponseEntity<?> getContactoByPersonaId(@PathVariable Long personaId) {
        try {
            List<ContactoDto> contactos = contacService.getContactoByPersonaId(personaId);
            if (contactos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron contactos para la Persona con ID: " + personaId);
            }
            return ResponseEntity.ok(contactos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener contactos por ID de Persona: " + e.getMessage());
        }
    }
}

