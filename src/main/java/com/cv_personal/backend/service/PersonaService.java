/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.PersonaDto;
import com.cv_personal.backend.mapper.PersonaMapper;
import com.cv_personal.backend.model.Persona;
import com.cv_personal.backend.repository.IPersonaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaService implements IPersonaService  {
    
    @Autowired
    private IPersonaRepository personaRep;
    
    @Autowired
    private PersonaMapper personaMap;
    
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
    public void deletePersona(Long id) {
        personaRep.deleteById(id);
    }

    @Override
    public PersonaDto updatePersona(Long id, PersonaDto personaDto) {
        Persona persona = personaRep.findById(id).orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));

        persona.setNombre(personaDto.getNombre());
        persona.setApellido(personaDto.getApellido());
        persona.setDescripcion_mi(personaDto.getDescripcion_mi());
        persona.setFecha_nacimiento(personaDto.getFecha_nacimiento());
        persona.setNum_celular(personaDto.getNum_celular());
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
