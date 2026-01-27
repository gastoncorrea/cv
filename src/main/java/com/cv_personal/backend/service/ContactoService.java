/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.ContactoDto;
import com.cv_personal.backend.mapper.ContactoMapper;
import com.cv_personal.backend.model.Contacto;
import com.cv_personal.backend.model.Persona; // Import Persona
import com.cv_personal.backend.repository.IContactoRepository;
import com.cv_personal.backend.repository.IPersonaRepository; // Import IPersonaRepository
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import jakarta.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional; // Import Transactional
@Service
public class ContactoService implements IContactoService{
    
    @Autowired
    private IContactoRepository contacRep;
    
    @Autowired
    private ContactoMapper contacMap;
    
    private final Path root = Paths.get("uploads");

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (java.io.IOException e) {
            throw new RuntimeException("No se pudo inicializar la carpeta para las subidas de archivos.", e);
        }
    }
    
    @Override
    public ContactoDto saveContacto(Contacto contacto) {
        Contacto contactoSave = contacRep.save(contacto);
        ContactoDto contactoDto = contacMap.toDto(contactoSave);
        
        return contactoDto;
    }

    @Override
    public List<ContactoDto> getContacto() {
        List<Contacto> contactos = contacRep.findAll();
        List<ContactoDto> contactosList = new ArrayList<>();
        
        for(Contacto contacto : contactos){
            contactosList.add(contacMap.toDto(contacto));
        }
        
        return contactosList;
    }

    @Override
    public ContactoDto findContacto(Long id) {
        Contacto contacto = contacRep.findById(id).orElse(null);
        ContactoDto contactoDto = contacMap.toDto(contacto);
        return contactoDto;
    }

    @Override
    public Contacto updateContacto(Long id) {
        Contacto contacto = contacRep.findById(id).orElse(null);
        return contacto;
    }

    @Override
    public void deleteContacto(Long id) {
        contacRep.deleteById(id);
    }
    
    @Override
    public ContactoDto updateLogoImage(Long id, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Falló al guardar un archivo vacío.");
            }
            
            Contacto contacto = contacRep.findById(id).orElseThrow(() -> new RuntimeException("Contacto no encontrado con id: " + id));

            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

            Path destinationFile = this.root.resolve(uniqueFilename).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "/uploads/" + uniqueFilename;
            contacto.setLogo_img(fileUrl);
            
            Contacto updatedContacto = contacRep.save(contacto);
            return contacMap.toDto(updatedContacto);

        } catch (java.io.IOException e) {
            throw new RuntimeException("Falló al guardar el archivo.", e);
        }
    }

    @Autowired
    private IPersonaRepository personaRepository; // Inject IPersonaRepository

    @Override
    @Transactional(readOnly = true)
    public List<ContactoDto> getContactoByPersonaId(Long personaId) {
        Persona persona = personaRepository.findById(personaId)
                                .orElseThrow(() -> new RuntimeException("Persona not found with ID: " + personaId));
        
        List<ContactoDto> listContactoDto = new ArrayList<>();
        for (Contacto contacto : persona.getContacto()) { // Access contacto directly
            listContactoDto.add(contacMap.toDto(contacto));
        }
        return listContactoDto;
    }
}
