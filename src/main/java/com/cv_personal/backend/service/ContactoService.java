/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.ContactoDto;
import com.cv_personal.backend.mapper.ContactoMapper;
import com.cv_personal.backend.model.Contacto;
import com.cv_personal.backend.repository.IContactoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactoService implements IContactoService{
    
    @Autowired
    private IContactoRepository contacRep;
    
    @Autowired
    private ContactoMapper contacMap;
    
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
    
}
