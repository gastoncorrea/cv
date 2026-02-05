/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.ContactoDto;
import com.cv_personal.backend.mapper.ContactoMapper;
import com.cv_personal.backend.model.Contacto;
import com.cv_personal.backend.model.Persona;
import com.cv_personal.backend.repository.IContactoRepository;
import com.cv_personal.backend.repository.IPersonaRepository;
import com.cv_personal.backend.util.FileUploadUtil; // Import FileUploadUtil
import jakarta.annotation.PostConstruct;
import java.io.IOException; // Import IOException
import java.nio.file.Files; // Import Files
import java.nio.file.Path; // Import Path
import java.nio.file.Paths; // Import Paths
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // Import Value
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ContactoService implements IContactoService{
    
    private static final Logger logger = LoggerFactory.getLogger(ContactoService.class);
    
    @Autowired
    private IContactoRepository contacRep;
    
    @Autowired
    private ContactoMapper contacMap;
    
    @Autowired
    private IPersonaRepository personaRepository;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Value("${uploads.directory}")
    private String uploadDir;
    
    @PostConstruct
    public void init() {
        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                logger.info("Created uploads directory: {}", uploadDir);
            }
        } catch (IOException e) {
            logger.error("Could not create uploads directory: {}", uploadDir, e);
            throw new RuntimeException("Could not create uploads directory", e);
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
        Contacto contacto = contacRep.findById(id)
                                    .orElseThrow(() -> new RuntimeException("Contacto no encontrado con ID: " + id));
        
        if (contacto.getLogo_img() != null && !contacto.getLogo_img().isEmpty()) {
            try {
                fileUploadUtil.deleteFile(uploadDir, contacto.getLogo_img());
                logger.info("Deleted old logo image: {}", contacto.getLogo_img());
            } catch (IOException e) {
                logger.warn("Could not delete old logo image {}: {}", contacto.getLogo_img(), e.getMessage());
            }
        }
        contacRep.deleteById(id);
    }
    
    @Override
    @Transactional
    public ContactoDto updateLogoImage(Long id, MultipartFile file) {
        Contacto contacto = contacRep.findById(id)
                                    .orElseThrow(() -> new RuntimeException("Contacto no encontrado con ID: " + id));

        // Delete old image if it exists
        if (contacto.getLogo_img() != null && !contacto.getLogo_img().isEmpty()) {
            try {
                fileUploadUtil.deleteFile(uploadDir, contacto.getLogo_img());
                logger.info("Deleted old logo image: {}", contacto.getLogo_img());
            } catch (IOException e) {
                logger.warn("Could not delete old logo image {}: {}", contacto.getLogo_img(), e.getMessage());
            }
        }

        if (file != null && !file.isEmpty()) {
            try {
                String newLogoPath = fileUploadUtil.saveFile(uploadDir, file);
                contacto.setLogo_img(newLogoPath);
                logger.info("Updated logo image for Contacto ID {}: {}", id, newLogoPath);
            } catch (IOException e) {
                logger.error("Could not save logo image for Contacto ID {}: {}", id, e.getMessage());
                throw new RuntimeException("Could not save logo image", e);
            }
        } else {
            contacto.setLogo_img(null);
            logger.info("Set logo image to null for Contacto ID {} as no file was provided.", id);
        }

        Contacto updatedContacto = contacRep.save(contacto);
        return contacMap.toDto(updatedContacto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactoDto> getContactoByPersonaId(Long personaId) {
        Persona persona = personaRepository.findById(personaId)
                                .orElseThrow(() -> new RuntimeException("Persona not found with ID: " + personaId));
        
        List<ContactoDto> listContactoDto = new ArrayList<>();
        for (Contacto contacto : persona.getContacto()) {
            listContactoDto.add(contacMap.toDto(contacto));
        }
        return listContactoDto;
    }
}
