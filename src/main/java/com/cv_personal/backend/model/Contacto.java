/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contactos")
@Getter  @Setter
public class Contacto {
    
    private Long id_contacto;
    private String nombre;
    private String url_contacto;
    private byte[] logo_img;
    
    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
    
}
