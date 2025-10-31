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
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id_usuario;
    @Email(message = "El formato del email es inválido")
    @Column(unique = true, nullable = false) 
    private String email;
    @Size(min = 3, max = 40, message = "El nombre debe tener entre 3 y 15 caracteres")
    @Column(length = 40)
    private String nombre;
    @Size(min = 8, max = 15, message = "El password debe tener de 8 a 15 caractes")
    @Column(length = 15)
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Rol> rol = new ArrayList<>();    
    
    
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private Persona persona;
    
}
