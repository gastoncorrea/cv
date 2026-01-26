/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.PersonaDto;
import com.cv_personal.backend.mapper.PersonaMapper;
import com.cv_personal.backend.model.Persona;
import com.cv_personal.backend.model.Usuario; // Import Usuario
import com.cv_personal.backend.repository.IPersonaRepository;
import com.cv_personal.backend.repository.IUsuarioRepository; // Import IUsuarioRepository
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;

@Service
public class PersonaService implements IPersonaService  {
    
    private static final Logger logger = LoggerFactory.getLogger(PersonaService.class);

    @Autowired
    private IPersonaRepository personaRep;
    
    @Autowired
    private IUsuarioRepository usuarioRep; // Inject IUsuarioRepository

    @Autowired
    private PersonaMapper personaMap;

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public PersonaDto savePersona(Persona persona) {
       Persona personaSave = personaRep.save(persona);
       PersonaDto personaDto = personaMap.toDto(personaSave);
       return personaDto;
    }

    @Override
    public List<PersonaDto> getPersonas() {
        List<Persona> listPersona = personaRep.findAll();
        List<PersonaDto> listPersonaDto = new ArrayList<>();
        
        for(Persona persona : listPersona){
            listPersonaDto.add(personaMap.toDto(persona));
        }
        return listPersonaDto;
    }

    @Override
    public PersonaDto findPersona(Long id) {
        Persona persona = personaRep.findById(id).orElse(null);
        PersonaDto personaDto = personaMap.toDto(persona);
        return personaDto;
    }

    @Override
    @Transactional
    public void deletePersona(Long id) {
        // Find the Persona entity by its ID
        personaRep.findById(id).ifPresent(persona -> {
            // Get the associated Usuario
            Usuario usuario = persona.getUsuario();

            // If a Usuario is associated
            if (usuario != null) {
                // Since Persona is the owning side of the relationship
                // (it has the @JoinColumn), we need to break the link
                // from the Persona side. We also nullify the reference
                // in the Usuario object to ensure consistency within the
                // persistence context.

                // Break the link on the inverse side (Usuario)
                usuario.setPersona(null);
                
                // Break the link on the owning side (Persona)
                persona.setUsuario(null);

                // Note: We don't need to save the usuario or persona entities
                // explicitly here because the changes are managed within the
                // transactional context. Hibernate will detect these changes
                // and update the objects. However, let's proceed to delete the persona.
            }

            // Now, delete the Persona. The transaction will ensure that the
            // link is broken before the deletion occurs.
            personaRep.delete(persona);
        });
    }

    @Override
    public PersonaDto updatePersona(Long id, PersonaDto personaDto) {
        Persona persona = personaRep.findById(id).orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));

        if (personaDto.getNombre() != null && !personaDto.getNombre().trim().isEmpty()) {
            persona.setNombre(personaDto.getNombre());
        }
        if (personaDto.getApellido() != null && !personaDto.getApellido().trim().isEmpty()) {
            persona.setApellido(personaDto.getApellido());
        }
        if (personaDto.getDescripcion_mi() != null && !personaDto.getDescripcion_mi().trim().isEmpty()) {
            persona.setDescripcion_mi(personaDto.getDescripcion_mi());
        }
        if (personaDto.getFecha_nacimiento() != null) { // LocalDate can be null
            persona.setFecha_nacimiento(personaDto.getFecha_nacimiento());
        }
        if (personaDto.getNum_celular() != null && !personaDto.getNum_celular().trim().isEmpty()) {
            persona.setNum_celular(personaDto.getNum_celular());
        }
        // El campo de la imagen no se actualiza aquí a propósito.
        // Se debe usar el método updateProfileImage para eso.

        Persona updatedPersona = personaRep.save(persona);
        return personaMap.toDto(updatedPersona);
    }

    private final java.nio.file.Path root = java.nio.file.Paths.get("uploads");

    @jakarta.annotation.PostConstruct
    public void init() {
        try {
            java.nio.file.Files.createDirectories(root);
        } catch (java.io.IOException e) {
            throw new RuntimeException("No se pudo inicializar la carpeta para las subidas de archivos.");
        }
    }

    @Override
    public PersonaDto updateProfileImage(Long id, org.springframework.web.multipart.MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Falló al guardar un archivo vacío.");
            }
            
            Persona persona = personaRep.findById(id).orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));

            // Generar un nombre de archivo único
            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = java.util.UUID.randomUUID().toString() + "_" + originalFilename;

            // Guardar el archivo
            java.nio.file.Path destinationFile = this.root.resolve(uniqueFilename).normalize().toAbsolutePath();
            java.nio.file.Files.copy(file.getInputStream(), destinationFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // Actualizar la entidad con la nueva URL
            String fileUrl = "/uploads/" + uniqueFilename;
            persona.setImagenUrl(fileUrl);
            
            Persona updatedPersona = personaRep.save(persona);
            return personaMap.toDto(updatedPersona);

        } catch (java.io.IOException e) {
            throw new RuntimeException("Falló al guardar el archivo.", e);
        }
    }
}
