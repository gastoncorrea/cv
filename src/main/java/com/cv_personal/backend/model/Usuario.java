/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id_usuario;
    @Email(message = "El formato del email es inválido")
    @Column(unique = true, nullable = false) 
    private String email;
    @Size(min = 8, max = 15, message = "El password debe tener de 8 a 15 caractes")
    @Column(length = 15, nullable =false)
    private String password;
    @Min(0)
    @Max(1)
    @Column(nullable = false)
    private int rol;
    
    
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private Persona persona;
    
}
