/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contactos")
@Getter  @Setter
public class Contacto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_contacto;
    @Size(min = 2, max = 45, message = "El nombre de la institucion debe tener entre 2 y 45 caracteres")
    @Column(length = 45, nullable = false)
    private String nombre;
    @Size(min = 2, max = 200, message = "El link del titulo debe tener entre 2 y 200 caracteres")
    @Column(length = 200)
    private String url_contacto;
    @Column(name = "logo_img", length = 255) // Assuming URL will be stored
    private String logo_img;
    
    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
    
}
