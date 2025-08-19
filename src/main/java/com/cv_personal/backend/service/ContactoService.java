/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.model.Contacto;
import com.cv_personal.backend.repository.IContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactoService implements IContactoService{
    
    @Autowired
    private IContactoRepository contacRep;
    
    @Override
    public void saveContacto(Contacto contacto) {
        contacRep.save(contacto);
    }
    
}
