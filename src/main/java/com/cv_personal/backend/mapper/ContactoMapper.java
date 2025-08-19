/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.mapper;

import com.cv_personal.backend.dto.ContactoDto;
import com.cv_personal.backend.model.Contacto;
import org.springframework.stereotype.Component;

@Component
public class ContactoMapper {
    
    public ContactoDto toDto(Contacto contacto){
        ContactoDto contactoDto = new ContactoDto();
        
        contactoDto.setId_contacto(contacto.getId_contacto());
        contactoDto.setNombre(contacto.getNombre());
        contactoDto.setUrl_contacto(contacto.getUrl_contacto());
        contactoDto.setLogo_img(contacto.getLogo_img());
        
        return contactoDto;
    }
}
