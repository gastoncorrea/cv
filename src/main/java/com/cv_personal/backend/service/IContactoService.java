/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cv_personal.backend.service;

import com.cv_personal.backend.dto.ContactoDto;
import com.cv_personal.backend.model.Contacto;
import java.util.List;


public interface IContactoService {
    
    public ContactoDto saveContacto(Contacto contacto);
    
    public List<ContactoDto> getContacto();
    
    public ContactoDto findContacto(Long id);
    
    public Contacto updateContacto(Long id);
    
    public void deleteContacto(Long id);
}
